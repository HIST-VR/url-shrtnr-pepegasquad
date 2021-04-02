package edu.kpi.testcourse.propertybased;

import com.google.gson.JsonObject;
import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.logic.UserActions;
import edu.kpi.testcourse.bigtable.BigTableImpl;
import edu.kpi.testcourse.model.User;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import java.util.ArrayList;
import javax.inject.Inject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.strings;

public class UserActionsTestPropertyBased {

  /****************************************************************************/

  @Inject
  @Client("/users/signin")
  private HttpClient client;

  @Test
  @Order(1)
  public void shouldAuthorizeUser_propertyBased() {
    qt()
      .forAll(
        strings().basicLatinAlphabet().ofLengthBetween(1, 10),
        strings().basicLatinAlphabet().ofLengthBetween(1, 10)
      ).check((email, password) -> {
        // WHEN
        User user = new User(email, password, new ArrayList<String>());
        UserActions.createUser(user);

        // THEN
      return ((UserActions.findUserByEmail(email)) && UserActions.checkPassword(email, password));
    });
  }
}

