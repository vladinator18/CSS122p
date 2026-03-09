package realestate.Presenter;

public class Property {

    private String unitCode;
    private int block;
    private int lot;
    private int area;
    private double price;
    private String status;

    private Property(Builder builder) {
        this.unitCode = builder.unitCode;
        this.block = builder.block;
        this.lot = builder.lot;
        this.area = builder.area;
        this.price = builder.price;
        this.status = builder.status;
    }

    public static class Builder {
        private String unitCode;
        private int block;
        private int lot;
        private int area;
        private double price;
        private String status;

        public Builder setUnitCode(String unitCode) { this.unitCode = unitCode; return this; }
        public Builder setBlock(int block) { this.block = block; return this; }
        public Builder setLot(int lot) { this.lot = lot; return this; }
        public Builder setArea(int area) { this.area = area; return this; }
        public Builder setPrice(double price) { this.price = price; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }

        public Property build() { return new Property(this); }
    }

    // Display for table
    public void display() {
        System.out.printf("%-10s %-6d %-6d %-8d %-10.2f %-10s\n",
                unitCode, block, lot, area, price, status);
    }

    // Getters for filtering
    public int getBlock() { return block; }
    public int getArea() { return area; }
    public String getStatus() { return status; }
}