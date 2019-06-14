if [ $# -eq 0 ];
then 
	echo "Please provide a stack name"
	exit 1
fi
stack_name=$1
aws cloudformation validate-template --template-body file://csye6225-cf-networking.json >/dev/null 2>&1
status=$?
if [ $status -ne 0 ];
then
        echo "Error in template"
        exit $status
fi
aws cloudformation create-stack --stack-name $stack_name --template-body file://csye6225-cf-networking.json --parameters file://parameters.json
status=$?
if [ $status -eq 0 ];
then 
	echo "Stack is being created...."
	aws cloudformation wait stack-create-complete --stack-name $stack_name
    status=$?
    if [ $status -eq 0 ];
    then
    	echo "Stack could not be created"
        exit $status
    fi
	echo "Stack created successfully!"
else 
	echo "Stack could not be created"
fi
