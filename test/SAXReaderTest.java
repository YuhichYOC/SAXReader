import com.yoclabo.reader.xml.NodeEntity;
import com.yoclabo.reader.xml.SAXReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SAXReaderTest {

    @Test
    public void test01() {
        String arg =
                "<root>" + "\n" +
                        "  <test1 attr1=\"attrvalue1\" attr2=\"attrvalue2\">" + "\n" +
                        "    Test1's Value" + "\n" +
                        "  </test1>" + "\n" +
                        "  <!-- test comment -->" + "\n" +
                        "  <test2 attr3=\"attrvalue3\" attr4=\"attrvalue4\"/>" + "\n" +
                        "</root> ";
        long startAt = System.currentTimeMillis();
        SAXReader p = new SAXReader(arg);
        int count_n = 0;
        int count_e = 0;
        int count_v = 0;
        int count_c = 0;
        int count_o = 0;
        do {
            NodeEntity n = p.next();
            switch (n.getType()) {
                case ELEMENT:
                    printNode(n);
                    ++count_n;
                    break;
                case EMPTY_ELEMENT:
                    printEmpty(n);
                    ++count_e;
                    break;
                case TEXT:
                    printValue(n);
                    ++count_v;
                    break;
                case COMMENT:
                    printComment(n);
                    ++count_c;
                    break;
                default:
                    printEndNode(n);
                    ++count_o;
                    break;
            }
        } while (!p.isEOF());
        long endAt = System.currentTimeMillis();
        System.out.println(" ------- End ------- ");
        System.out.printf("Millisecond : %d%n", endAt - startAt);
        assertEquals(2, count_n);
        assertEquals(1, count_e);
        assertEquals(1, count_v);
        assertEquals(1, count_c);
        assertEquals(2, count_o);
    }

    private void printNode(NodeEntity arg) {
        System.out.println(" ------- Node ------- ");
        System.out.println("Node Name : " + arg.getNodeName());
        if (!arg.getAttributes().isEmpty()) {
            arg.getAttributes().forEach(a -> {
                System.out.println("Attribute Name : " + a.getAttrName());
                System.out.println("Attribute Value : " + a.getAttrValue());
            });
        }
    }

    private void printEmpty(NodeEntity arg) {
        System.out.println(" ------- Empty Node ------- ");
        System.out.println("Node Name : " + arg.getNodeName());
        if (!arg.getAttributes().isEmpty()) {
            arg.getAttributes().forEach(a -> {
                System.out.println("Attribute Name : " + a.getAttrName());
                System.out.println("Attribute Value : " + a.getAttrValue());
            });
        }
    }

    private void printEndNode(NodeEntity arg) {
        System.out.println(" ------- End Node ------- ");
        System.out.println("Node Name : " + arg.getNodeName());
    }

    private void printValue(NodeEntity arg) {
        System.out.println(" ------- Value ------- ");
        System.out.println("Value : " + arg.getNodeValue());
    }

    private void printComment(NodeEntity arg) {
        System.out.println(" ------- Comment ------- ");
        System.out.println("Comment : " + arg.getNodeValue());
    }

}