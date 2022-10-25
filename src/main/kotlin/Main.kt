import java.sql.DriverManager
import java.sql.SQLException


fun main(args: Array<String>) {
    val url = "jdbc:postgresql://url"
    val user = "user"
    val password = "pass"

    try {
        DriverManager.getConnection(url, user,  password).use { con ->
            con.createStatement().use { st ->
                st.executeQuery("SELECT VERSION()").use { rs ->
                    if (rs.next()) {
                        println(rs.getString(1))
                    }
                }
            }
        }
    } catch (ex: SQLException) {
        throw ex
    }
}
