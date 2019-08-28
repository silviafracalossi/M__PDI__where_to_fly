
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Class that represent the customer user
 */
@Entity
@Table(name = "CUSTOMERUSER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CustomerUser implements Serializable {

    @Id
    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    /**
     * Accesses the nickname
     * @return nickname of calling airport
     */
    public String get_nickname() {
        return nickname;
    }

    /**
     * Changes the nickname
     * @param nickname new nickname
     */
    public void set_nickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Accesses the firstname
     * @return firstname of calling airport
     */
    public String get_firstname() {
        return firstname;
    }

    /**
     * Changes the firstname
     * @param firstname new firstname
     */
    public void set_firstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Accesses the lastname
     * @return lastname of calling airport
     */
    public String get_lastname() {
        return lastname;
    }

    /**
     * Changes the lastname
     * @param lastname new lastname
     */
    public void set_lastname(String lastname) {
        this.lastname = lastname;
    }


    /**
     * Compares the calling customer_user and the passed object
     * @param obj object to be compared
     * @return Bool
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof CustomerUser) {
            CustomerUser o = (CustomerUser) obj;
            return (o.nickname.equals(this.nickname));
        }
        return false;
    }

    /**
     * Print method
     * @return String of information of the calling user
     */
    @Override
    public String toString() {
        return "CustomerUser " + this.firstname + " " + this.lastname +", with nickname " + this.nickname + ";";
    }

}
