#!/bin/bash
echo "\e[32m\e[1mSCRIPT TO CREATE NETWORK RESOURCES USING AWS CLI\e[0m"

#Setting default values if parameters have not been passed
STACK_NAME="$1"
if [ -z "$STACK_NAME" ]
then
	echo "\e[31m\e[1m ERROR : STACK NAME WAS NOT PROVIDED!\e[0m"
	exit 1
fi
CIDR_BLOCK="${2:-10.0.0.0/16}"

#Creating VPC
echo "Creating a VPC with name $1 and CIDR Block $2"
vpc_id=$(aws ec2 create-vpc --cidr-block "$CIDR_BLOCK" --query "Vpc.VpcId" --output text)
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE CREATING VPC!\e[0m"
        exit $status
fi

#Waiting until vpc is avaialable
aws ec2 wait vpc-available --vpc-ids $vpc_id

#Setting the VPC Name
echo "Setting name : $STACK_NAME-csye6225-vpc to VPC with VPC ID : $vpc_id"
aws ec2 create-tags --resources $vpc_id --tags 'Key=Name,Value='$STACK_NAME'-csye6225-vpc'
vpc_name=$(aws ec2 describe-vpcs --vpc-ids $vpc_id --query "Vpcs[0].Tags[0].Value" --output text)

#Fetching availability zones
current_region=$(aws configure get region)
echo "Fetching 3 availability zone ids in the region $current_region"
availability_zone_1=$(aws ec2 describe-availability-zones --query "AvailabilityZones[0].ZoneId" --output text)
availability_zone_2=$(aws ec2 describe-availability-zones --query "AvailabilityZones[1].ZoneId" --output text)
availability_zone_3=$(aws ec2 describe-availability-zones --query "AvailabilityZones[2].ZoneId" --output text)

#Setting default values for Subnet CIDR blocks if not passed by the user
SUBNET1_CIDR_BLOCK="${3:-10.0.0.0/24}"
SUBNET2_CIDR_BLOCK="${4:-10.0.1.0/24}"
SUBNET3_CIDR_BLOCK="${5:-10.0.2.0/24}"

#Creating subnets
echo "Creating subnet in availability zone $availability_zone_1 and CIDR block $SUBNET1_CIDR_BLOCK"
subnet1_id=$(aws ec2 create-subnet --vpc-id "$vpc_id" --cidr-block "$SUBNET1_CIDR_BLOCK" --availability-zone-id "$availability_zone_1" --query "Subnet.SubnetId" --output text)
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE CREATING SUBNET 1!\e[0m"
        exit $status
fi

echo "Creating subnet in availability zone $availability_zone_2 and CIDR block $SUBNET2_CIDR_BLOCK"
subnet2_id=$(aws ec2 create-subnet --vpc-id "$vpc_id" --cidr-block "$SUBNET2_CIDR_BLOCK" --availability-zone-id "$availability_zone_2" --query "Subnet.SubnetId" --output text)
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE CREATING SUBNET 2!\e[0m"
        exit $status
fi

echo "Creating subnet in availability zone $availability_zone_3 and CIDR block $SUBNET3_CIDR_BLOCK"
subnet3_id=$(aws ec2 create-subnet --vpc-id "$vpc_id" --cidr-block "$SUBNET3_CIDR_BLOCK" --availability-zone-id "$availability_zone_3" --query "Subnet.SubnetId" --output text)
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE CREATING SUBNET 3!\e[0m"
        exit $status
fi

#Creating an Internet Gateway
echo "Creating Internet Gateway"
gateway_id=$(aws ec2 create-internet-gateway --query "InternetGateway.InternetGatewayId" --output text)
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE CREATING INTERNET GATEWAY!\e[0m"
        exit $status
fi

#Attaching Internet Gateway to the VPC
echo "Attaching Internet Gateway $gateway_id to the VPC $vpc_id"
aws ec2 attach-internet-gateway --vpc-id "$vpc_id" --internet-gateway-id "$gateway_id"
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE ATTACHING INTERNET GATEWAY $gateway_id TO VPC $vpc_id!\e[0m"
        exit $status
fi

#Creating a Route Table
echo "Creating a Route Table"
route_table_id=$(aws ec2 create-route-table --vpc-id $vpc_id --query "RouteTable.RouteTableId" --output text)
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE CREATING A ROUTE TABLE!\e[0m"
        exit $status
fi

#Associating each subnet with the created Route Table
echo "Associating Subnet $subnet1_id with Route Table $route_table_id"
subnet1_assosciation_id=$(aws ec2 associate-route-table --route-table-id "$route_table_id" --subnet-id "$subnet1_id")
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE ASSOCIATING SUBNET $subnet1_id WITH THE PUBLIC ROUTE TABLE $route_table_id!\e[0m"
        exit $status
fi
echo "Associating Subnet $subnet2_id with Route Table $route_table_id"
subnet2_association_id=$(aws ec2 associate-route-table --route-table-id "$route_table_id" --subnet-id "$subnet2_id")
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE ASSOCIATING SUBNET $subnet2_id WITH THE PUBLIC ROUTE TABLE $route_table_id!\e[0m"
        exit $status
fi
echo "Associating Subnet $subnet3_id with Route Table $route_table_id"
subnet3_association_id=$(aws ec2 associate-route-table --route-table-id "$route_table_id" --subnet-id "$subnet3_id")
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE ASSOCIATING SUBNET $subnet3_id WITH THE PUBLIC ROUTE TABLE $route_table_id!\e[0m"
        exit $status
fi

#Creating a public route in the public route table with destination CIDR block 0.0.0.0/0 and internet gateway creted above as the target
echo "Creating a public route in the Route Table $route_table_id"
route_creation_status=$(aws ec2 create-route --route-table-id "$route_table_id" --destination-cidr-block "0.0.0.0/0" --gateway-id "$gateway_id")
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE CREATING PUBLIC ROUTE!\e[0m"
        exit $status
fi

echo "\e[32m\e[1mNETWORK RESOURCES CREATION WAS SUCCESSFUL!!\e[0m"
