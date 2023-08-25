package tacos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(force=true)
@RequiredArgsConstructor
public class Ingredient {

  private final String name;
  private final Type type;

  // 역직렬화할 때 생성자가 없다고 오류가 나왔음
//  @JsonCreator
//  public Ingredient(@JsonProperty("name") String name, @JsonProperty("type") Type type) {
//    this.name = name;
//    this.type = type;
//  }
  
  public static enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }
  
}
