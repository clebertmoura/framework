
package br.com.framework.service.adapter;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapter extends XmlAdapter<String, Date> {

	public Date unmarshal(String value) {
		return (br.com.framework.service.converter.DateTimeConverter.parse(value));
	}

	public String marshal(Date value) {
		return (br.com.framework.service.converter.DateTimeConverter.format(value));
	}

}
