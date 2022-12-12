package org.example.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Violation {
    @SerializedName("type")
    private String type;
    @SerializedName("fine_amount")
    private Double fineAmount;
}
