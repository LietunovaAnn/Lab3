package org.example.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "ViolationsStatistic")
@Getter
@Setter
public class ViolationsStatisticList {

    @JacksonXmlProperty(localName = "violation")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Violation> violationStatisticList = new ArrayList<>();
}
