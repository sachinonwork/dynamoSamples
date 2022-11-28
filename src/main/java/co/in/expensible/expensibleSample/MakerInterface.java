package co.in.expensible.expensibleSample;

import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.finspacedata.model.AwsCredentials;
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

    
    static final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_WEST_1).build();
    static final DynamoDB dynamoDB = new DynamoDB(client);

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MakerResult> createMaker(@RequestBody MakerEntity maker) {

        LOG.info("Recevied maker request for {}", maker.getId());

        Table dynamoTable = dynamoDB.getTable("makerDetails");

        //DynamoDB dynamoDB = new DynamoDB(client);
        // Create a map of key-value where key represents attribute name and
        // value represents attribute value
        //
        Item itemInDB = new Item().withPrimaryKey("id", maker.getId())
                .withString("itemName", maker.getItemName())
                .withString("contactNumber", maker.getContactNumber())
                .withString("contactEmail", maker.getContactEmail());

        dynamoTable.putItem(itemInDB);

        MakerResult makerResult = new MakerResult(maker);
        LOG.debug("Date of maker is:{}", makerResult.getCreatedAt());
        return ResponseEntity.ok().body(makerResult);
    }

}
