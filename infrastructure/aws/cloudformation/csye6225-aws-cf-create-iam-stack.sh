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
CODEDEPLOY_BUCKET_NAME=$3
if [ -z "$CODEDEPLOY_BUCKET_NAME" ]; then
	echo "\e[31m\e[1m ERROR : S3 BUCKET NAME FOR CODEDEPLOY WAS NOT PROVIDED!\e[0m"
	exit 1
fi
IMAGE_UPLOAD_BUCKET_NAME=$4
if [ -z "$IMAGE_UPLOAD_BUCKET_NAME" ]; then
	echo "\e[31m\e[1m ERROR : S3 BUCKET NAME FOR IMAGE UPLOAD WAS NOT PROVIDED!\e[0m"
	exit 1
fi
APPLICATION_NAME=$5	
if [ -z "$APPLICATION_NAME" ]; then
	echo "\e[31m\e[1m ERROR : CODEDEPLOY APPLICATION NAME WAS NOT PROVIDED!\e[0m"
	exit 1
fi

echo "\e[32m\e[1mCREATING IAM STACK!!\e[0m"
stackCreation=$(aws cloudformation create-stack --stack-name $STACK_NAME --template-body file://csye6225-cf-iam.json --capabilities CAPABILITY_NAMED_IAM --parameters \
ParameterKey=CircleCIUser,ParameterValue=$USER_NAME ParameterKey=ApplicationName,ParameterValue=$APPLICATION_NAME ParameterKey=CodeDeployS3Bucket,ParameterValue=$CODEDEPLOY_BUCKET_NAME ParameterKey=ImageUploadS3Bucket,ParameterValue=$IMAGE_UPLOAD_BUCKET_NAME)
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

