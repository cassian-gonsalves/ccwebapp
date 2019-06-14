#!/bin/bash
echo "\e[32m\e[1mSCRIPT TO TEARDOWN NETWORK RESOURCES USING AWS CLI\e[0m"

VPC_ID="$1";
if [ -z "$VPC_ID" ]
then
	echo "\e[31m\e[1m ERROR : VPC ID WAS NOT PROVIDED!\e[0m"
	exit 0
fi

vpc=$(aws ec2 describe-vpcs --filters Name=vpc-id,Values="$VPC_ID" --output text)
if [ -z "$vpc" ]
then
	echo "\e[31m\e[1m ERROR : VPC WITH VPC ID $VPC_ID DOES NOT EXIST !\e[0m"
	exit 0
fi

#Finding Custom Route Table ID
route_table_id=$(aws ec2 describe-route-tables --filters Name=vpc-id,Values="$VPC_ID" Name=association.main,Values=false --query 'RouteTables[*].{RouteTableId:RouteTableId}' --output text)
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE FINDING ROUTE TABLE ID!\e[0m"
        exit $status
fi

#Finding Internet Gateway ID
internet_gateway_id=$(aws ec2 describe-internet-gateways --filters Name=attachment.vpc-id,Values="$VPC_ID" --query 'InternetGateways[*].{InternetGatewayId:InternetGatewayId}' --output text)
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE FINDING INTERNET GATEWAY ID!\e[0m"
        exit $status
fi

#Deleting Subnets in the VPC
subnets=$(aws ec2 describe-subnets --filters Name=vpc-id,Values="$VPC_ID" --query 'Subnets[*].SubnetId' --output text)
status=$?
if [ $status -ne 0 ]
then
       	echo "\e[31m\e[1m ERROR WHILE SEARCHING FOR SUBNETS!\e[0m"
    	exit $status
fi
for subnet_id in $subnets
do
    aws ec2 delete-subnet --subnet-id $subnet_id
	status=$?
	if [ $status -ne 0 ];
	then
        	echo "\e[31m\e[1m ERROR WHILE DELETING SUBNET $subnet_id!\e[0m"
        	exit $status
	fi
	echo "DELETED SUBNET $subnet_id"
done

#Deleting Route Table
aws ec2 delete-route-table --route-table-id $route_table_id
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE DELETING ROUTE TABLE $route_table_id!\e[0m"
        exit $status
fi
echo "DELETED ROUTE TABLE $route_table_id"

#Detaching Internet Gateway
aws ec2 detach-internet-gateway --internet-gateway-id $internet_gateway_id --vpc-id $VPC_ID
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE DETACHING INTERNET GATEWAY $internet_gateway_id!\e[0m"
        exit $status
fi
echo "DETATCHED INTERNET GATEWAY $internet_gateway_id"

#Deleting Internet Gateway
aws ec2 delete-internet-gateway --internet-gateway-id $internet_gateway_id
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE DELETING INTERNET GATEWAY $internet_gateway_id!\e[0m"
        exit $status
fi
echo "DELETED INTERNET GATEWAY $internet_gateway_id"

#Deleting VPC
aws ec2 delete-vpc --vpc-id $VPC_ID
status=$?
if [ $status -ne 0 ];
then
        echo "\e[31m\e[1m ERROR WHILE DELETING VPC $VPC_ID!\e[0m"
        exit $status
fi
echo "DELETED VPC $VPC_ID"
echo "\e[32m\e[1mNETWORK RESOURCES DELETION WAS SUCCESSFUL!!\e[0m"