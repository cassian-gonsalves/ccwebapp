STACK_NAME=$1
KEY_NAME=$2
BucketName=$3
AMI_ID=$4
APPLICATION_NAME=$5
if [ -z "$1" ]; then
	echo "Please provide a stack name"
	exit 1
fi
if [ -z "$2" ]; then
	echo "Please provide a Aws keyPair name"
	exit 1
fi
if [ -z "$3" ]; then
	echo "Please provide a Image Upload S3 Bucket Name"
	exit 1
fi
if [ -z "$4" ]; then
	echo "Please provide AMI ID"
	exit 1
fi
if [ -z "$5" ]; then
	APPLICATION_NAME="csye6225-webapp"
fi	
echo "Creating application stack"
stackCreation=$(aws cloudformation create-stack --stack-name $STACK_NAME --template-body file://csye6225-cf-application.json --parameters ParameterKey=stackName,ParameterValue=$STACK_NAME ParameterKey=keyPair,ParameterValue=$KEY_NAME ParameterKey=amiId,ParameterValue=$AMI_ID ParameterKey=s3Bucket,ParameterValue=$BucketName ParameterKey=ApplicationName,ParameterValue=$APPLICATION_NAME)
if [ $? -eq 0 ]; then
	stackCompletion=$(aws cloudformation wait stack-create-complete --stack-name $STACK_NAME)
	if [ $? -eq 0 ]; then
		echo "Stack creation successful"
	else
		echo "Error in creating CloudFormation"
	fi
else
	echo "Error in creating CloudFormation"
	echo $stackCreation
fi