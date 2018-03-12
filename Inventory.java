import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

class Inventory {

    int itemNO = 10000;
    private JPanel Panel1;
    JFrame frame = new JFrame("Inventory Tracker");
    ArrayList<JButton> myB = new ArrayList<JButton>();
    Panel p = new Panel();
    String desc = "Broken";
    String inv = "Broken";
    String iNo = "Broken";
    Dimension d = new Dimension(400, 50);
    Boolean Decrease = false;
    String dbName = "";

    public Connection getLoginConnection() throws SQLException {
        String url = "";
        String user = "";
        String pass = "";
        Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }

    public Connection getConnection() throws SQLException {
        String url = ""+dbName;
        String user = "";
        String pass = "";
        Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }

        public int indexOfItemNO(){
        int x = 0;
        Connection myConn = null;
        try {
            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * from inventory");

            // iterate through the java resultset
            while(rs.next())

            {

                x++;
            }
            myConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }
        return x;
    }


    private void appendItem() {
        final JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton button1 = new JButton();

        JTextField xyzField = new JTextField(5);
        JTextField xyField = new JTextField(5);
        JTextField xField = new JTextField(5);
        frame.setSize(500, 150);
        button1.setSize(200,24);
        button1.setText("Submit");
        frame.add(panel);

        JLabel ItemNo = new JLabel("Item NO");
        JLabel Description = new JLabel("Description");
        JLabel Inventory = new JLabel("Inventory");

        panel.add(ItemNo);
        panel.add(xyzField);
        panel.add(Description);
        panel.add(xyField);
        panel.add(Inventory);
        panel.add(xField);
        panel.add(button1);
        frame.setVisible(true);


        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String ItemNumber = xyzField.getText();
                String Description = xyField.getText();
                String Inventory = xField.getText();
                Connection myConn = null;
                try {
                    myConn = getConnection();
                    Statement myStmt = myConn.createStatement();
                    try {
                        myStmt.executeUpdate("UPDATE inventory SET Inventory = '" + Inventory+ "', Description = '" + Description + "' WHERE ItemNo = " + ItemNumber);
                    }catch (SQLException a){
                        a.printStackTrace();
                    }finally {
                        myConn.close();
                        myStmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }        finally {
                    try {
                        myConn.close();
                    }catch (SQLException a){
                        a.printStackTrace();
                    }

                }

                frame.dispose();
                myB.clear();
                addButtons();
                displayButtons();

            }
        });

    }

    private void addItem() {

        final JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton button1 = new JButton();

        JTextField xyField = new JTextField(5);
        JTextField xField = new JTextField(5);
        frame.setSize(500, 150);
        button1.setSize(200,24);
        button1.setText("Submit");
        frame.add(panel);

        int testNumber = indexOfItemNO();
        testNumber = testNumber + 10001;

        String ItemNumber = Integer.toString(testNumber);

        JLabel ItemNo = new JLabel("Item NO");
        JLabel ItemNoPull = new JLabel(ItemNumber);
        JLabel Description = new JLabel("Description");
        JLabel Inventory = new JLabel("Inventory");

        panel.add(ItemNo);
        panel.add(ItemNoPull);
        panel.add(Description);
        panel.add(xyField);
        panel.add(Inventory);
        panel.add(xField);
        panel.add(button1);
        frame.setVisible(true);





        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String Description = xyField.getText();
                String Inventory = xField.getText();
                Connection myConn = null;
                Statement myStmt = null;
                try {
                    myConn = getConnection();
                    myStmt = myConn.createStatement();
                    try {
                        myStmt.executeUpdate("insert into inventory "
                                + "(ItemNo, Description, Inventory)"
                                + " values('"+ItemNumber+"', '"+Description+"', '"+Inventory+"')");
                        myConn.close();
                        frame.dispose();
                        myB.clear();
                        addButtons();
                        displayButtons();
                    } catch (SQLException a){
                        a.printStackTrace();
                    }finally {
                        myConn.close();
                        myStmt.close();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }        finally {
                    try {
                        myConn.close();
                    }catch (SQLException a){
                        a.printStackTrace();
                    }

                }
            }
        });

    }

    private void deleteItem() {
        Connection myConn = null;
        try {
            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {
                int testNumber = indexOfItemNO();
                testNumber = testNumber + 10000;

                String ItemNumber = Integer.toString(testNumber);
                myStmt.executeUpdate("DELETE from inventory WHERE ItemNo = "+ItemNumber);

                JOptionPane.showMessageDialog(null, "Item " + ItemNumber +" Deleted");

                myB.clear();
                addButtons();
                displayButtons();
            }catch (SQLException a){
                a.printStackTrace();
            }finally {
                myStmt.close();
                myConn.close();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }

    }

    private void refreshItem(){
        addButtons();
        displayButtons();
    }


    public void addToolbar (){
        JButton append = new JButton("Append");
        append.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendItem();
            }
        });
        JButton addItem = new JButton("Add");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();

            }
        });
        JButton deleteItem = new JButton("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        JToolBar toolbar = new JToolBar();
        toolbar.add(addItem);
        toolbar.add(append);
        toolbar.add(deleteItem);
        frame.add(toolbar, BorderLayout.NORTH);
    }


    private static String sold(int x) {
        return "UPDATE inventory " +
                "SET Inventory = Inventory-1 " +
                "WHERE ItemNo = " + x + " ";
    }



    public void Sold(int x) {
        Connection myConn = null;
        try {
            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {
                myStmt.executeUpdate(sold(x));
                JOptionPane.showMessageDialog(null, "Item Sold");
                myB.clear();
                addButtons();
                displayButtons();
            }catch (SQLException a){
                myConn.close();
                myStmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }

    }
    public int getTotalSold(int z){
        int x = 0;

        Connection myConn = null;
        try {
            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {
                ResultSet rs = myStmt.executeQuery("SELECT * from salestracker " +
                        "WHERE ItemNo = " + z);

                // iterate through the java resultset
                while (rs.next())

                {

                    x++;

                }
            }catch (SQLException a){
                a.printStackTrace();
            }finally {
                myConn.close();
                myStmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }
        return x;
    }

    public String getButtonText(int x) {
        Connection myConn = null;
        try {

            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {
                ResultSet rs = myStmt.executeQuery(
                        "SELECT Description, Inventory, ItemNo "
                                + "FROM inventory "
                                + "WHERE ItemNo = " + x);

                // iterate through the java resultset
                while (rs.next())

                {
                    desc = rs.getString("Description");
                    inv = rs.getString("Inventory");
                    iNo = rs.getString("ItemNo");

                }
            }catch (SQLException a){
                a.printStackTrace();
            }finally {
                myConn.close();
                myStmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }

        return iNo + " : " + desc + " : " +inv;
    }

    //  while i < products in inventory
    //  add a new Jbutton to the arrayList myB

    public void addButtons(){

        int i = 0;
        while ( i < indexOfItemNO()) {
            i++;
            itemNO = 10000;
            myB.add(new JButton());
            //IMP

        }
    }
    public String currentDateMinus30(){
        String test = null;

        Connection myConn = null;
        try {
            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {

                myStmt.executeUpdate("UPDATE date SET Date = CURDATE() - INTERVAL 31 DAY WHERE id = 101");
                ResultSet rs = myStmt.executeQuery("SELECT * from date");
                while (rs.next()) {

                    test = rs.getString("Date");
                }
            }catch (SQLException a){
                a.printStackTrace();
            }finally {
                myConn.close();
                myStmt.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }
        return test;
    }
    public int get30DaySold(int z){
        int x = 0;
        Connection myConn = null;
        try {
            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {
                ResultSet rs = myStmt.executeQuery("SELECT * from salestracker " +
                        "WHERE ItemNo = '" + z + "' AND Date > '" + currentDateMinus30() + "'");

                // iterate through the java resultset
                while (rs.next())

                {

                    x++;

                }
            }catch (SQLException a){
                a.printStackTrace();
            }finally {
                myConn.close();
                myStmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }

        return x;
    }

    public String getInventory(int x) {
        Connection myConn = null;
        try {

            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {
                ResultSet rs = myStmt.executeQuery(
                        "SELECT Inventory "
                                + "FROM inventory "
                                + "WHERE ItemNo = " + x);
                // iterate through the java resultset
                while (rs.next())

                {
                    inv = rs.getString("Inventory");


                }
            }catch (SQLException a){
                a.printStackTrace();
            }finally {
                myConn.close();
                myStmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }

        return inv;
    }

    public double getSellingPrice(int x, boolean y, String z){
        double SellingPrice = 0.0;
        if(z.equals("Website")){
            if(!y){

                Connection myConn = null;
                try {
                    myConn = getConnection();
                    Statement myStmt = myConn.createStatement();
                    try {
                        String test = "SELECT WebsitePrice from inventory where ItemNo = " + x;
                        ResultSet rs = myStmt.executeQuery(test);

                        // iterate through the java resultset
                        while (rs.next())

                        {
                            SellingPrice = rs.getDouble(1);

                        }
                    }catch (SQLException a){
                        a.printStackTrace();
                    }finally {
                        myConn.close();
                        myStmt.close();
                    }
                }catch (SQLException e) {
                    e.printStackTrace();

                }        finally {
                    try {
                        myConn.close();
                    }catch (SQLException a){
                        a.printStackTrace();
                    }

                }
            }else{

                Connection myConn = null;
                try {
                    myConn = getConnection();
                    Statement myStmt = myConn.createStatement();
                    try {
                        String test = "SELECT WebsiteNailPrice from inventory where ItemNo = " + x;
                        ResultSet rs = myStmt.executeQuery(test);

                        // iterate through the java resultset
                        while (rs.next())

                        {
                            SellingPrice = rs.getDouble(1);

                        }
                    }catch (SQLException a){
                        myConn.close();
                        myStmt.close();
                    }
                }catch (SQLException e) {
                    e.printStackTrace();

                }        finally {
                    try {
                        myConn.close();
                    }catch (SQLException a){
                        a.printStackTrace();
                    }

                }
            }

        }else if(z.equals("Ebay")){
            if(!y){

                Connection myConn = null;
                try {
                    myConn = getConnection();
                    Statement myStmt = myConn.createStatement();
                    try {
                        String test = "SELECT EbayPrice from inventory where ItemNo = " + x;
                        ResultSet rs = myStmt.executeQuery(test);

                        // iterate through the java resultset
                        while (rs.next())

                        {
                            SellingPrice = rs.getDouble(1);

                        }
                    }catch (SQLException a){
                        a.printStackTrace();
                    }finally {
                        myConn.close();
                        myStmt.close();
                    }

                    myConn.close();
                }catch (SQLException e) {
                    e.printStackTrace();

                }        finally {
                    try {
                        myConn.close();
                    }catch (SQLException a){
                        a.printStackTrace();
                    }

                }
            }else{

                Connection myConn = null;
                try {
                    myConn = getConnection();
                    Statement myStmt = myConn.createStatement();
                    try {
                        String test = "SELECT EbayNailPrice from inventory where ItemNo = " + x;
                        ResultSet rs = myStmt.executeQuery(test);

                        // iterate through the java resultset
                        while (rs.next())

                        {
                            SellingPrice = rs.getDouble(1);

                        }
                    }catch (SQLException a){
                        a.printStackTrace();
                    }finally {
                        myConn.close();
                        myStmt.close();
                    }
                    myConn.close();
                }catch (SQLException e) {
                    e.printStackTrace();

                }        finally {
                    try {
                        myConn.close();
                    }catch (SQLException a){
                        a.printStackTrace();
                    }

                }
            }
        }


        return SellingPrice;
    }

    public double getSellingCost(int x, boolean y, String z){
        double SellingPrice = 0.0;
        double ShippingCost = 0.0;
        String sqlChunk = "";
        Boolean webOrBay = true;
        Boolean nailIncluded = false;
        if(z.equals("Website")){
            webOrBay = true;
            if(!y){
                nailIncluded = false;
                sqlChunk = "WebsitePrice";
            }else {
                nailIncluded = true;
                sqlChunk = "WebsiteNailPrice";
            }
        }else if(z.equals("Ebay")){
            webOrBay = false;
            if(!y){
                nailIncluded =false;
                sqlChunk = "EbayPrice";
            }else {
                nailIncluded = true;
                sqlChunk = "EbayNailPrice";
            }
        }

        Connection myConn = null;
        try {
            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {
                String test = "SELECT " + sqlChunk + ", ShippingCost from inventory where ItemNo = " + x;
                ResultSet rs = myStmt.executeQuery(test);

                // iterate through the java resultset
                while (rs.next())

                {
                    SellingPrice = rs.getDouble(1);
                    ShippingCost = rs.getDouble(2);

                }
            }catch (SQLException a){
                a.printStackTrace();
            }finally {
                myConn.close();
                myStmt.close();
            }

            myConn.close();
        }catch (SQLException e) {
            e.printStackTrace();

        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }
        double EbayFee = SellingPrice * 0.1;
        double PaypalFee = SellingPrice * 0.029 +0.3;
        if(webOrBay) {
            if(!nailIncluded) {
                return PaypalFee + ShippingCost;
            }else {
                return PaypalFee + ShippingCost + 3.11;
            }
        }
        else {
            if(!nailIncluded) {
                return EbayFee + PaypalFee + ShippingCost;
            }else {
                return EbayFee + PaypalFee + ShippingCost + 3.11;
            }
        }
    }

    public double getPurchasePrice(int x){

        double PurchasePrice = 0.0;

        Connection myConn = null;
        try {
            myConn = getConnection();
            Statement myStmt = myConn.createStatement();
            try {
                String test = "SELECT PurchasePrice from inventory where ItemNo = " + x;
                ResultSet rs = myStmt.executeQuery(test);

                // iterate through the java resultset
                while (rs.next())

                {
                    PurchasePrice = rs.getDouble(1);

                }
            }catch (SQLException a){
                a.printStackTrace();
            }finally {
                myConn.close();
                myStmt.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();

        }        finally {
            try {
                myConn.close();
            }catch (SQLException a){
                a.printStackTrace();
            }

        }

        return PurchasePrice;
    }

    public double getNetProfit(double x, double y, double z){
        return x - (y+z);
    }


    //  for each Jbutton created by addButton()
    //  set size = d (initialized under inventory())
    //  set text = getButtonText()
    //  On Jbutton press initiate sold() based on index of button.
    Boolean nailIncludedBool = false;
    public void displayButtons()

    {
        DecimalFormat df = new DecimalFormat("#.#");
        p.removeAll();
        frame.getContentPane().add(p);


        for (JButton btn : myB)

        {
            btn.setPreferredSize(d);
            itemNO++;
            int test = myB.lastIndexOf(btn) + 10001;
            double days = 30.0;
            double dailySold = (get30DaySold(test)/(days));
            double inventoryOverDailySold = Integer.parseInt(getInventory(test))/ dailySold;
            btn.setText(getButtonText(itemNO));
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    final JFrame frame2 = new JFrame();
                    JPanel panel = new JPanel();
                    JCheckBox checkbox2 = new JCheckBox("Decrease Inventory");

                    String [] test1 = {"Website","Ebay","Total"};
                    Box box3 = Box.createVerticalBox();
                    JComboBox PlatformCheck = new JComboBox(test1);
                    Box box1 = Box.createVerticalBox();


                    JCheckBox nailIncluded = new JCheckBox("Nail Included");
                    nailIncluded.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            nailIncludedBool = e.getStateChange()==1;
                            if (PlatformCheck.getSelectedItem().equals("Website")) {
                                box1.removeAll();

                                JLabel SellingPrice = new JLabel("Website Selling Price: $" + getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()));
                                JLabel SellingCost = new JLabel("Website Selling Cost: $" + df.format(getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString())));
                                JLabel PurchasePrice = new JLabel("Purchase Price: $" + getPurchasePrice(test));
                                JLabel NetProfit = new JLabel("Net Profit: $" + df.format(getNetProfit(getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getPurchasePrice(test))));
                                panel.setLayout(new BoxLayout(panel, Y_AXIS));

                                box1.add(Box.createVerticalStrut(15));
                                box1.add(SellingPrice);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(SellingCost);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(PurchasePrice);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(NetProfit);
                                box1.add(Box.createVerticalStrut(15));
                                box1.revalidate();
                            }else if (PlatformCheck.getSelectedItem().equals("Ebay")){
                                box1.removeAll();

                                JLabel SellingPrice = new JLabel("Ebay Selling Price: $" + getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()));
                                JLabel SellingCost = new JLabel("Ebay Selling Cost: $" + df.format(getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString())));
                                JLabel PurchasePrice = new JLabel("Purchase Price: $" + getPurchasePrice(test));
                                JLabel NetProfit = new JLabel("Net Profit: $" + df.format(getNetProfit(getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getPurchasePrice(test))));
                                panel.setLayout(new BoxLayout(panel, Y_AXIS));

                                box1.add(Box.createVerticalStrut(15));
                                box1.add(SellingPrice);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(SellingCost);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(PurchasePrice);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(NetProfit);
                                box1.add(Box.createVerticalStrut(15));
                                box1.revalidate();
                            }
                        }
                    });
                    PlatformCheck.setMaximumSize(new Dimension(200, PlatformCheck.getMinimumSize().height));
                    JLabel Info = new JLabel( getButtonText(test));
                    JLabel TotalSold = new JLabel("Total Sold: " + getTotalSold(test));
                    JLabel ThirtyDaySold = new JLabel("30 Day Sold: " + get30DaySold(test));
                    Box box = Box.createVerticalBox();
                    box.add(Info);
                    box.add(Box.createVerticalStrut(15));
                    box.add(TotalSold);
                    box.add(Box.createVerticalStrut(15));
                    box.add(ThirtyDaySold);
                    box.add(Box.createVerticalStrut(15));
                    box.add(PlatformCheck);
                    box.add(nailIncluded);
                    panel.add(box);
                    panel.add(box1);
                    panel.add(box3);
                    JLabel SellingPrice = new JLabel("Website Selling Price: $" + getSellingPrice(test,nailIncludedBool, "Website"));
                    JLabel SellingCost = new JLabel( "Website Selling Cost: $" + df.format(getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString())));
                    JLabel PurchasePrice = new JLabel( "Purchase Price: $" + getPurchasePrice(test));
                    JLabel NetProfit = new JLabel( "Net Profit: $" + df.format(getNetProfit(getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getPurchasePrice(test))));
                    panel.setLayout(new BoxLayout(panel, Y_AXIS));

                    box1.add(Box.createVerticalStrut(15));
                    box1.add(SellingPrice);
                    box1.add(Box.createVerticalStrut(15));
                    box1.add(SellingCost);
                    box1.add(Box.createVerticalStrut(15));
                    box1.add(PurchasePrice);
                    box1.add(Box.createVerticalStrut(15));
                    box1.add(NetProfit);
                    box1.add(Box.createVerticalStrut(15));
                    PlatformCheck.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            if(PlatformCheck.getSelectedItem().equals("Website")){
                                box1.removeAll();

                                JLabel SellingPrice = new JLabel("Website Selling Price: $" + getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()));
                                JLabel SellingCost = new JLabel( "Website Selling Cost: $" + df.format(getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString())));
                                JLabel PurchasePrice = new JLabel( "Purchase Price: $" + getPurchasePrice(test));
                                JLabel NetProfit = new JLabel( "Net Profit: $" + df.format(getNetProfit(getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getPurchasePrice(test))));
                                panel.setLayout(new BoxLayout(panel, Y_AXIS));

                                box1.add(Box.createVerticalStrut(15));
                                box1.add(SellingPrice);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(SellingCost);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(PurchasePrice);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(NetProfit);
                                box1.add(Box.createVerticalStrut(15));
                                box1.revalidate();



                            }else if(PlatformCheck.getSelectedItem().equals("Ebay")) {
                                box1.removeAll();
                                JLabel SellingPrice = new JLabel("Ebay Selling Price: $" + getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()));
                                JLabel SellingCost = new JLabel( "Ebay Selling Cost: $" + df.format(getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString())));
                                JLabel PurchasePrice = new JLabel( "Purchase Price: $" + getPurchasePrice(test));
                                JLabel NetProfit = new JLabel( "Net Profit: $" + df.format(getNetProfit(getSellingPrice(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getSellingCost(test, nailIncludedBool, PlatformCheck.getSelectedItem().toString()),getPurchasePrice(test))));

                                box1.add(Box.createVerticalStrut(15));
                                box1.add(SellingPrice);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(SellingCost);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(PurchasePrice);
                                box1.add(Box.createVerticalStrut(15));
                                box1.add(NetProfit);
                                box1.add(Box.createVerticalStrut(15));
                                box1.revalidate();

                            }else if((PlatformCheck.getSelectedItem().toString()).equals("Total")){
                                box1.removeAll();
                                box1.revalidate();
                            }


                        }
                    });


                    box3.add(checkbox2);
                    checkbox2.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            Decrease = e.getStateChange()==1;
                        }
                    });
                    JButton button1 = new JButton("Save + Close");
                    box3.add(Box.createVerticalStrut(15));
                    button1.setSize(d);
                    box3.add(button1);
                    button1.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            if(Decrease){
                                Sold(test);
                            }

                            frame2.dispose();

                        }
                    });


                    frame2.add(panel);
                    frame2.setSize(600, 500);
                    frame2.setVisible(true);



                    //

                }


            });
            p.add(btn);
            //IMP
        }
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1000);
        frame.setVisible(true);
    }


    private JFrame loginTest() {
        final JFrame frame2 = new JFrame();
        JPanel panel = new JPanel();
        Box box1 = Box.createVerticalBox();
        JLabel user = new JLabel("Username");
        JTextField Jusername = new JTextField(15);
        Jusername.setSize(d);
        JLabel pass = new JLabel("Password");
        JPasswordField JPassword = new JPasswordField(15);
        Jusername.setSize(d);
        JButton Button = new JButton("Login");
        panel.setVisible(true);
        panel.add(box1);
        box1.setVisible(true);
        box1.add(user);
        box1.add(Jusername);
        box1.add(pass);
        box1.add(JPassword);
        box1.add(Button);
        frame2.add(panel);
        frame2.setSize(300, 200);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String username = Jusername.getText();
                String password = JPassword.getText();
                //convert get text to validate username and password
                Connection myConn = null;
                try {
                    myConn = getLoginConnection();
                    Statement myStmt = myConn.createStatement();
                    try {
                        int x = 1;
                        ResultSet rs = myStmt.executeQuery("SELECT Username from usernames ");

                        // iterate through the java resultset
                        while (rs.next())

                        {
                            if(username.equals(rs.getString("Username"))) {
                                System.out.println("correct user");
                                ResultSet rs1 = myStmt.executeQuery("SELECT Password from usernames where Username = '" + username + "'");

                                while (rs1.next())

                                {
                                    if (password.equals(rs1.getString("Password"))) {
                                        ResultSet rs2 = myStmt.executeQuery("SELECT dbName from usernames where Username = '" + username + "'");

                                        while (rs2.next())

                                        {
                                            dbName = rs2.getString("DBName");
                                        }
                                        addButtons();
                                        displayButtons();
                                        addToolbar();
                                        frame2.dispose();
                                    } else {
                                        JOptionPane.showMessageDialog(frame2, "Sorry that password is not correct");
                                    }
                                }
                            }else {
                                JOptionPane.showMessageDialog(frame2, "Sorry that username is not correct");
                                break;
                            }
                        }
                    }catch (SQLException a){
                        a.printStackTrace();
                    }finally {
                        myConn.close();
                        myStmt.close();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        myConn.close();
                    }catch (SQLException a){
                        a.printStackTrace();
                    }

                }

            }
        });
        return frame2;

    }



    public static void main(String[] args){
        Inventory a = new Inventory();
        a.loginTest();

    }

}
