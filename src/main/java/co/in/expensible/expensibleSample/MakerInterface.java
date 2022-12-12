package co.in.expensible.expensibleSample;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/maker")
public class MakerInterface {

    private static final Logger LOG = LoggerFactory.getLogger(MakerInterface.class);

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MakerResult> createMaker(@RequestBody MakerEntity maker) {

        LOG.debug("Recevied maker request for {}", maker.getId());
        AWSCredentialsProvider credProvider = AWSConnector.loadCredentials(false);
        // AmazonDynamoDB awsClient = AmazonDynamoDBClientBuilder.standard()
        //       .withCredentials(credProvider)
        //        .withEndpointConfiguration(new AwsClientBuilder().)
        //       .withRegion(Regions.EU_WEST_1)
        //      .build();

        AmazonDynamoDB awsClient = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        DynamoDB dynamoDb = new DynamoDB(awsClient);
        //LOG.debug("Credentials used with dynamo connection: {} ", awsClient.get);
        Table dynamoTable = dynamoDb.getTable("makerDetails");

        //DynamoDB dynamoDB = new DynamoDB(client);
        // Create a map of key-value where key represents attribute name and
        // value represents attribute value
        //
        Item itemInDB = new Item().withPrimaryKey("id", maker.getId())
                .withString("itemName", maker.getItemName());
                //.withString("contactNumber", maker.getContactNumber())
                //.withString("contactEmail", maker.getContactEmail());

        dynamoTable.putItem(itemInDB);

        MakerResult makerResult = new MakerResult(maker);
        LOG.debug("Date of maker is:{}", makerResult.getCreatedAt());
        return ResponseEntity.ok().body(makerResult);
    }

}
