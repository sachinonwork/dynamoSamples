package co.in.expensible.expensibleSample;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceAsyncClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AWSConnector {

    private static final Logger LOG = LoggerFactory.getLogger(AWSConnector.class);

    public static AWSCredentialsProvider loadCredentials(boolean isLocal) {
        final AWSCredentialsProvider credentialsProvider;
        if (isLocal) {
            AWSSecurityTokenService stsClient = AWSSecurityTokenServiceAsyncClientBuilder.standard()
                    .withRegion("eu-west-1")
                    .build();

            LOG.debug("AWS credentials used for: {}", stsClient.getSessionToken().getCredentials());
            AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest().withDurationSeconds(3600)
                    .withRoleArn("arn:aws:iam::269809496862:role/developer")
                    .withRoleSessionName("dynamoSession");

            AssumeRoleResult assumeRoleResult = stsClient.assumeRole(assumeRoleRequest);
            Credentials creds = assumeRoleResult.getCredentials();

            credentialsProvider = new AWSStaticCredentialsProvider(
                    new BasicSessionCredentials(creds.getAccessKeyId(),
                            creds.getSecretAccessKey(),
                            creds.getSessionToken())
            );
        } else {
            credentialsProvider = new DefaultAWSCredentialsProviderChain();
        }

        return credentialsProvider;
    }

}
