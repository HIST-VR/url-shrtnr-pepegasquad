package edu.kpi.testcourse.stresstesting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.bigtable.BigTableImpl;
import edu.kpi.testcourse.logic.UserActions;
import edu.kpi.testcourse.model.User;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import javax.inject.Inject;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class ShortenerLoadTest extends AbstractJavaSamplerClient {

  @Override
  public void setupTest(JavaSamplerContext context){
    // TODO Auto-generated method stub

    super.setupTest(context);
  }
  @Override
  public Arguments getDefaultParameters() {
    Arguments defaultParameters = new Arguments();
    defaultParameters.addArgument("login", "ImenUser1");
    defaultParameters.addArgument("password","ImenUser@");
    return defaultParameters;
  }

  @Inject
  @Client("/users/signin")
  private HttpClient client;

  @Override
  public SampleResult runTest(JavaSamplerContext arg0) {
    // TODO Auto-generated method stub
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("email", "signin@gmail.com");
    jsonObject.addProperty("password", "password");
    SampleResult result = new SampleResult();
    boolean success = true;
    result.sampleStart();
    // Write your test code here.

    HttpRequest<String> request = HttpRequest.POST("/", jsonObject.toString());
    var response = client.toBlocking().exchange(request, String.class);
    assertEquals(HttpStatus.OK, response.getStatus());

    var body = Main.getGson().fromJson(response.getBody().get(), JsonObject.class);
    assertThat(BigTableImpl.tokens).contains(body.get("access_token").getAsString());


    result.sampleEnd();

    result.setSuccessful(success);

    return result;

  }

}
