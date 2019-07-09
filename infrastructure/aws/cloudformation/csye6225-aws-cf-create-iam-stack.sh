#!/bin/bash -xe
if [ -z "$1" ]; then
	echo "\e[31m\e[1m ERROR : STACK NAME WAS NOT PROVIDED!\e[0m"
	exit 1
else
	STACK_NAME="$1-csye6225-iam"
fi
USER_NAME=$2
if [ -z "$USER_NAME" ]; then
	echo "\e[31m\e[1m ERROR : CIRCLECI USER'S USERNAME WAS NOT PROVIDED!\e[0m"
	exit 1
fi
BUCKET_NAME=$3
if [ -z "$BUCKET_NAME" ]; then
	echo "\e[31m\e[1m ERROR : S3 BUCKET NAME FOR CODEDEPLOY WAS NOT PROVIDED!\e[0m"
	exit 1
fi
APPLICATION_NAME=$4	
if [ -z "$APPLICATION_NAME" ]; then
	echo "\e[31m\e[1m ERROR : CODEDEPLOY APPLICATION NAME WAS NOT PROVIDED!\e[0m"
	exit 1
fi

echo "\e[32m\e[1mCREATING IAM STACK!!\e[0m"
stackCreation=$(aws cloudformation create-stack --stack-name $STACK_NAME --template-body file://csye6225-cf-iam.json --capabilities CAPABILITY_IAM --parameters \
ParameterKey=UserName,ParameterValue=$USER_NAME ParameterKey=ApplicationName,ParameterValue=$APPLICATION_NAME ParameterKey=BucketName,ParameterValue=$BUCKET_NAME)
if [ $? -eq 0 ]; then
	stackCompletion=$(aws cloudformation wait stack-create-complete --stack-name $STACK_NAME)
	if [ $? -eq 0 ]; then
		echo "\e[32m\e[1mIAM STACK CREATION WAS SUCCESSFUL!!\e[0m"
	else
		echo "\e[31m\e[1m ERROR WHILE CREATING IAM STACK!\e[0m"
	fi
else
	echo "\e[31m\e[1m ERROR WHILE CREATING IAM STACK!\e[0m"
	echo $stackCreation
fi

