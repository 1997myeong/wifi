package project.wifi.dto;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Result {
    @SerializedName("CODE")
    private String code;

    @SerializedName("MESSAGE")
    private String message;
}
