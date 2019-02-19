
package br.com.framework.service.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Base64BinaryAdapter
    extends XmlAdapter<String, String>
{


    public String unmarshal(String value) {
        return new String(value);
    }

    public String marshal(String value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
