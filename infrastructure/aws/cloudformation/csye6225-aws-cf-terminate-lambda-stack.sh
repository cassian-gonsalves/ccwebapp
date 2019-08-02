#!bin/bash -xe
if [ $# -eq 0 ]; then
        echo "Please provide a stack name"
        exit 1
fi
stack_name="$1-csye6225-lambda"
aws cloudformation list-stack-resources --stack-name $stack_name
if [ $? -ne "0" ]; then
        echo "Stack does not exit"
        exit 1
fi

aws cloudformation delete-stack --stack-name $stack_name
ret=$?
if [ $ret -eq 0 ];
then
        echo "stack is being deleted...."
	aws cloudformation wait stack-delete-complete --stack-name $stack_name	
	echo "stack deleted successfully!!"
else
       	echo "stack could not be deleted"
fi