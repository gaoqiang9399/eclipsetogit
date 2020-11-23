package app.tech.formCompare.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
/**
 * 加密解密xml文件
 * @author WANGCONG
 *
 */
public class DocXmlUtil
{
  protected Document getDoc()
    throws Exception
  {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.newDocument();
    return doc;
  }

  protected Document file2Document(File file)
    throws Exception
  {
    ObjectInputStream ois = null;
    Document document = null;
    try {
      ois = new ObjectInputStream(new FileInputStream(file));
      document = (Document)ois.readObject();
    }
    catch (Exception e) {
      throw new Exception(e);
    }finally {
      if(ois != null)
      {
        ois.close();
      }
    }
    return document;
  }

  protected void document2File(File file, Document document)
    throws Exception
  {
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(new FileOutputStream(file));
      oos.writeObject(document);
      oos.flush();
    } catch (Exception e) {
      System.out.println("加密写入文件失败！" + e.getMessage());
      throw new Exception(e);
    }finally {
      if(oos != null)
      {
        oos.close();
      }
    }
  }

//  public static void main(String[] args) throws Exception {
//    DocXmlUtil dx = new DocXmlUtil();
//    File xmlFile = new File("G:/temp/xml/formdemo0001.xml");
//
//    Document doc2 = dx.docParse(xmlFile);
//    dx.save(xmlFile, doc2);
//  }

  protected Document docParse(File xmlFile)
  {
    Document doc = null;
    try {
      doc = file2Document(xmlFile);
    } catch (Exception e) {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      try
      {
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(xmlFile);
      } catch (Exception e1) {
        System.out.println(xmlFile.getName() + e1.getMessage());
      }
      doc.normalize();
    }
    return doc;
  }

  protected void save(File f, Document doc) {
    String encodeFlag = "unencode";
    //encodeFlag = null;
    if ((encodeFlag != null) && ("unencode".equals(encodeFlag))) {
        saveFileUnencode(f, doc);
    } else {
        try {
          document2File(f, doc);
        } catch (Exception e) {
          saveFileUnencode(f, doc);
        }
    }
  }

  private void saveFileUnencode(File f, Document doc) {
    Writer out = null;
    try {
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      Properties properties = transformer.getOutputProperties();
      properties.setProperty("method", "xml");
      properties.setProperty("encoding", "utf-8");
      properties.setProperty("version", "1.0");

      properties.setProperty("indent", "yes");
      transformer.setOutputProperties(properties);

      DOMSource source = new DOMSource(doc);
      out = new OutputStreamWriter(new FileOutputStream(f));
      StreamResult result = new StreamResult(new FileOutputStream(f));
      transformer.transform(source, result);
      out.flush();
    } catch (Exception e2) {
      e2.printStackTrace();
      System.out.println(e2.getMessage());
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
