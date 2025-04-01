// package carrental.local.carrental.Entity;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "cars")
// public class Car {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false, length = 100)
//     private String name; // Hãng xe (Toyota, Honda,...)

//     @Column(nullable = false, length = 100)
//     private String brand; // Hãng xe (Toyota, Honda,...)

//     @Column(nullable = false, length = 100)
//     private String model; // Dòng xe (Camry, Civic,...)

//     @Column(nullable = false)
//     private int year; // Năm sản xuất

//     @Column(nullable = false, length = 20)
//     private String licensePlate; // Biển số xe

//     @Column(nullable = false)
//     private double rentalPrice; // Giá thuê mỗi ngày

//     @Column(nullable = false)
//     private Boolean status;

//     public Car() {}

//     public Car(String name, String brand, String model, int year, String licensePlate, double rentalPrice, boolean status) {
//         this.name = name;
//         this.brand = brand;
//         this.model = model;
//         this.year = year;
//         this.licensePlate = licensePlate;
//         this.rentalPrice = rentalPrice;
//         this.status = status;
//     }

//     // Getters & Setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }
//     public String getBrand() { return brand; }
//     public void setBrand(String brand) { this.brand = brand; }

//     public String getModel() { return model; }
//     public void setModel(String model) { this.model = model; }

//     public int getYear() { return year; }
//     public void setYear(int year) { this.year = year; }

//     public String getLicensePlate() { return licensePlate; }
//     public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

//     public double getRentalPrice() { return rentalPrice; }
//     public void setRentalPrice(double rentalPrice) { this.rentalPrice = rentalPrice; }

//     public Boolean getStatus() { return status; }
//     public void setStatus(Boolean status) { this.status = status; }


// }

package carrental.local.carrental.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, length = 20)
    private String licensePlate;

    @Column(nullable = false)
    private double rentalPrice;

    @Column(nullable = false)
    private Boolean status;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image; // Lưu trữ ảnh dưới dạng byte array

    public Car() {}

    public Car(String name, String brand, String model, int year, String licensePlate, double rentalPrice, boolean status, byte[] image) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.rentalPrice = rentalPrice;
        this.status = status;
        this.image = image;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public double getRentalPrice() { return rentalPrice; }
    public void setRentalPrice(double rentalPrice) { this.rentalPrice = rentalPrice; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
}
