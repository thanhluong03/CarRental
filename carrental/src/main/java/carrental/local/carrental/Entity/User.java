package carrental.local.carrental.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false, length = 20, unique = true)
    private String cccd;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] driverLicenseImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RentalOrder> rentals;

    public User() {}

    public User(String fullName, int age, String phoneNumber, String gender, String cccd, byte[] driverLicenseImage) {
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.cccd = cccd;
        this.driverLicenseImage = driverLicenseImage;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public byte[] getDriverLicenseImage() { return driverLicenseImage; }
    public void setDriverLicenseImage(byte[] driverLicenseImage) { this.driverLicenseImage = driverLicenseImage; }
}