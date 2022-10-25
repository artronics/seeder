import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.sql.DriverManager
import java.sql.SQLException


fun main(args: Array<String>) {
    val parser = ArgParser("seeder")
    val host by parser.option(ArgType.String, shortName = "h", fullName = "host", description = "database host").required()
    val port by parser.option(ArgType.String, shortName = "p", fullName = "port", description = "port").required()
    val db by parser.option(ArgType.String, shortName = "d", fullName = "db", description = "database name").required()
    val user by parser.option(ArgType.String, shortName = "U", fullName = "user", description = "database user").required()
    val password by parser.option(ArgType.String, shortName = "P", fullName = "password", description = "database password").required()
    parser.parse(args)

    val url = "postgres://$host/$db"

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
