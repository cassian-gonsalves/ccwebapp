#!/bin/bash -xe
if [ -z "$1" ]; then
	echo "\e[31m\e[1m ERROR : STACK NAME WAS NOT PROVIDED!\e[0m"
	exit 1
else
	STACK_NAME="$1-csye6225-lambda"
fi
EMAIL_DOMAIN_NAME=$2
if [ -z "$EMAIL_DOMAIN_NAME" ]; then
	echo "\e[31m\e[1m ERROR : EMAIL DOMAIN NAME WAS NOT PROVIDED!\e[0m"
	exit 1
fi
echo "Creating serverless stack"
stackCreation=$(aws cloudformation create-stack --stack-name $STACK_NAME --template-body file://csye6225-cf-lambda.json --parameters ParameterKey=domainName,ParameterValue=$EMAIL_DOMAIN_NAME)
if [ $? -eq 0 ]; then
	stackCompletion=$(aws cloudformation wait stack-create-complete --stack-name $STACK_NAME)
	if [ $? -eq 0 ]; then
		echo "Stack creation successful"
		fnUpdate=$(aws lambda update-function-configuration --function-name PasswordResetLambda --handler ResetUserPassword::handleRequest --runtime java8)
	else
		echo "Error in creating CloudFormation"
	fi
else
	echo "Error in creating CloudFormation"
	echo $stackCreation
fi
 