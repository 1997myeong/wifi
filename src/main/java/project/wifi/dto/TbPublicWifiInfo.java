package project.wifi.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.ArrayList;


@Getter
public class TbPublicWifiInfo {

    @SerializedName("list_total_count")
    private String listTotalCount;

    @SerializedName("RESULT")
    private Result result;

    @SerializedName("row")
    private ArrayList<WifiDto> row = new ArrayList<>();
}
