
# Transaction Analysis

The objective of this usecase is to find the counterparty with which the user had done highest amount transfer

#### Note

- Data types are assumed based on input data provided in requirement
- For the purpose of demo, the final output data is printed to console before writing to file
- Test cases will check if the data and schema of actual output matches the expected output

#### Tools

- jdk 1.8
- scala 2.11.8
- sbt 1.7.1
- spark 2.4.3

#### Running

- Install SBT and GIT
- Clone the project and navigate to the local path where project is cloned
- Execute sbt *testOnly* command to run the unit test case
- Execute sbt *assembly* command to build the jar
- Copy the jar to cluster
- Copy the input file transaction_data.csv(available in src/main/resource) to desired hdfs path
- Provide the jar path in the spark submit command
- The spark submit command accepts two arguments
  1. hdfs input file path eg: /user/analytics/input/transaction_data.csv
  2. hdfs output path eg: /user/analytics/output
- Execute the spark submit command
- Output will be printed in console and written as csv file to the path given as <hdfs path>/output.csv
```sh
spark2-submit --master yarn --deploy-mode client --driver-memory 1g --driver-cores 1 --executor-memory 1g --executor-cores 1 --num-executors 2 --class com.se.TransactionAnalysis /<path to jar>/transaction-analysis-assembly-0.1.0-SNAPSHOT.jar <hdfs path of input file> <hdfs path of output file>
```


#### Version

- 0.1.0 - initial version with required transformation and unit testcase

#### Authors

- Kevin George

