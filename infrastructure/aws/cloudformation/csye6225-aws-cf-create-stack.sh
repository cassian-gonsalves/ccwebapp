#!bin/bash
if [ $# -eq 0 ];
then 
	echo "Please provide a stack name"
	exit 1
fi
stack_name=$1



AWSREGION1=$2
AWSREGION2=$3
AWSREGION3=$4
VPC_NAME=${stack_name}-csye6225-vpc
SUBNET1_NAME=${stack_name}-csye6225-subnet1
SUBNET2_NAME=${stack_name}-csye6225-subnet2
SUBNET3_NAME=${stack_name}-csye6225-subnet3
IGNAME=${stack_name}-csye6225-InternetGateway
PUBLIC_ROUTE_TABLE=${stack_name}-csye6225-public-route-table

aws cloudformation create-stack --stack-name $stack_name --template-body file://csye6225-cf-networking.json --parameters ParameterKey=VPCName,ParameterValue=$VPC_NAME ParameterKey=SubnetName1,ParameterValue=$SUBNET1_NAME ParameterKey=SubnetName2,ParameterValue=$SUBNET2_NAME ParameterKey=SubnetName3,ParameterValue=$SUBNET3_NAME ParameterKey=PublicRouteTableName,ParameterValue=$PUBLIC_ROUTE_TABLE ParameterKey=AWSREGION1,ParameterValue=$AWSREGION1 ParameterKey=AWSREGION2,ParameterValue=$AWSREGION2 ParameterKey=AWSREGION3,ParameterValue=$AWSREGION3 ParameterKey=IGName,ParameterValue=$IGNAME

status=$?
if [ $status -eq 0 ];
then 
	echo "Stack is being created...."
	aws cloudformation wait stack-create-complete --stack-name $stack_name
    status=$?
    if [ $status -eq 0 ];
    then
    	echo "Stack created successfully!"
        exit $status
    fi
	echo "Stack could not be created"
else 
	echo "Stack could not be created"
fi
