import java.time.LocalDate;

public class Lesson3 {
    public static void main(String[] args) {

        Product productsArray[] = new Product[5];
        productsArray[0]= new Product("Samsung s25 Ultra", LocalDate.of(2025,01,02), "Samsung Corp.", "Korea", 5599,true);
        productsArray[1]= new Product("Samsung s30", LocalDate.of(2025,5,12), "Samsung Corp.", "Korea", 5999,true);
        productsArray[2]= new Product("Samsung A50", LocalDate.of(2025,10,6), "Samsung Corp.", "China", 9999,false);
        productsArray[3]= new Product("Port Wifi", LocalDate.of(2023,12,3), "Port", "China", 18000,true);
        productsArray[4]= new Product("Router", LocalDate.of(2022,8,22), "HR", "China", 6000,false);
        for(
                Product product: productsArray)

        {
            product.productInfo();
        }
        Product.Park park = new Product.Park("Summer");
        Product.Park.Attraction aa = park.new Attraction("Батут", "12:00 - 22:00", 100);
        Product.Park.Attraction ab = park.new Attraction("ТИР", "12:00 - 22:00", 100);

        aa.parkInfo();
        ab.parkInfo();

    }
        public static class Product {
            private String name;
            private LocalDate productionDate;
            private String manufacturer;
            private String countryOfOrigin;
            private double price;
            private boolean reserved;

            public Product(String name, LocalDate productionDate, String manufacturer, String countryOfOrigin, double price, boolean reserved) {
                this.name = name;
                this.productionDate = productionDate;
                this.manufacturer = manufacturer;
                this.countryOfOrigin = countryOfOrigin;
                this.price = price;
                this.reserved = reserved;
            }

            public void productInfo() {
                System.out.println("Название: " + name);
                System.out.println("Дата производства: " + productionDate);
                System.out.println("Производитель: " + manufacturer);
                System.out.println("Страна происхождения: " + countryOfOrigin);
                System.out.println("Цена: " + price + " Руб.");
                System.out.println("Забронирован: " + (reserved ? "Да" : "Нет"));
            }

                public static class Park {
                private String parkName;

                 public Park(String parkName) {
                    this.parkName = parkName;
                 }

                    public class Attraction {
                    private String attractionName;
                    private String workingHours;
                    private double price;

                    public Attraction(String attractionName, String workingHours, double price) {
                        this.attractionName = attractionName;
                        this.workingHours = workingHours;
                        this.price = price;
                    }

                    public void parkInfo() {
                        System.out.println("Парк: " + parkName);
                        System.out.println("Атракцион: " + attractionName);
                        System.out.println("Режим работы: " + workingHours);
                        System.out.println("Цена: " + price);

                    }
                }
            }


        }
    }