package edu.kpi.testcourse.propertybased;

import static org.assertj.core.api.Assertions.assertThat;

import edu.kpi.testcourse.logic.UserActions;
import org.junit.jupiter.api.Test;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.strings;

public class HashTestPropertyBased {

  @Test
  void propertyTestEqualsHashForOneObject() {
    qt()
      .forAll(
        strings().basicLatinAlphabet().ofLengthBetween(1, 10)
      ).check((firstPass) -> {

      String first = UserActions.hash(firstPass);
      String second = UserActions.hash(firstPass);

      assertThat(first).isEqualTo(second); // is not a boolean result

      // THEN if no exception
      return true;
    });
  }

  @Test
  void propertyTestEqualsHashForDifferentValues() {
    qt()
      .forAll(
        strings().basicLatinAlphabet().ofLengthBetween(1, 8),
        strings().basicLatinAlphabet().ofLengthBetween(8, 10)
      ).check((firstPass, secondPass) -> {

      String first = UserActions.hash(firstPass);
      String second = UserActions.hash(secondPass);

      assertThat(first).isNotEqualTo(second); // is not a boolean result

      // THEN if no exception
      return true;
    });
  }
}
