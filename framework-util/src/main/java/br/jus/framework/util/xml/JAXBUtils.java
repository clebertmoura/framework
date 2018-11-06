package br.jus.framework.util.xml;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Classe Utilitária para conversão entre Objeto e XML
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public abstract class JAXBUtils {

	/**
	 * Converte uma um objeto do tipo <T> informado para uma String XML.
	 * 
	 * @param object
	 * @param classType
	 * @return
	 * @throws JAXBException 
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String marshal(Object object, Class classType) throws JAXBException {
		return marshal(object, classType, true);
	}
	
	/**
	 * Converte uma o objeto informado para uma String XML.
	 * 
	 * @param object
	 * @param classType
	 * @param formatOutput
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String marshal(Object object, Class classType, boolean formatOutput) throws JAXBException {
		JAXBContext context = null;
		if (classType.isAnnotationPresent(XmlRootElement.class)) {
			context = JAXBContext.newInstance(classType);
		} else {
			context = JAXBContext.newInstance(classType.getPackage().getName());
		}
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatOutput);
		QName qName = null;
		if (classType.isAnnotationPresent(XmlType.class)) {
			XmlType xmlType = (XmlType) classType.getAnnotation(XmlType.class);
			qName = new QName(xmlType.namespace(), xmlType.name());
		} else {
			qName = new QName("", WordUtils.uncapitalize(classType.getSimpleName()));
		}
		StringWriter writer = new StringWriter();
		JAXBElement<?> wrappedElement =  
				new JAXBElement(qName, classType, object);
		String result = null;
		try {
			marshaller.marshal(wrappedElement, writer);
			result = writer.toString();
		} finally {
			IOUtils.closeQuietly(writer);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(WordUtils.uncapitalize("GrupoPermissaoAcao"));
	}
	/*public static <T> String marshal(T object, Class<T> classType) {
		JAXBContext context = null;
		Marshaller marshaller = null;
		try {
			context = JAXBContext.newInstance(classType.getPackage().getName());
			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		QName qName = null;
		if (classType.isAnnotationPresent(XmlType.class)) {
			XmlType xmlType = classType.getAnnotation(XmlType.class);
			qName = new QName(xmlType.namespace(), xmlType.name());
		} else {
			qName = new QName(classType.getSimpleName().toLowerCase());
		}
		StringWriter writer = new StringWriter();
		JAXBElement<T> wrappedProcesso =  
				new JAXBElement<T>(qName, classType, object);
		String result = null;
		try {
			marshaller.marshal(wrappedProcesso, writer);
			result = writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
		return result;
	}*/
	
	
	
	/**
	 * Converte uma String XML para um objeto do tipo <T> informado.
	 * Utiliza o encoding default: UTF-8.
	 * 
	 * @param xml
	 * @param classType
	 * @return
	 */
	public static <T> T unmarshal(String xml, Class<T> classType) {
		T object = null;
		try {
			object = unmarshal(xml, classType, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return object;
	}
	
	/**
	 * Converte uma String XML para um objeto do tipo <T> informado.
	 * 
	 * @param xml
	 * @param classType
	 * @param encoding
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static <T> T unmarshal(String xml, Class<T> classType, String encoding) throws UnsupportedEncodingException {
		JAXBContext context = null;
		Unmarshaller unmarshaller = null;
		try {
			context = JAXBContext.newInstance(classType.getPackage().getName());
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		ByteArrayInputStream input = new ByteArrayInputStream (xml.getBytes(encoding));
		JAXBElement<T> jaxbElement = null;
		try {
			jaxbElement = unmarshaller.unmarshal(new StreamSource(input), classType);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return jaxbElement.getValue();
	}

}
