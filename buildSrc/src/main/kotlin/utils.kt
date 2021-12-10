import org.gradle.api.Project

private val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
private val snakeRegex = "_[a-zA-Z]".toRegex()

private fun String.toSnakeCase(): String =
    camelRegex.replace(this) { "_${it.value}" }.toLowerCase()

private fun String.toCamelCase(): String =
    snakeRegex.replace(this) { it.value.replace("_", "").toUpperCase() }

fun Project.findPropertyAsString(propertyName: String, envName: String? = null): String? {
    val value = project.findProperty(propertyName) as String?
    if (value != null && value.isNotBlank()) return value
    return System.getenv(envName ?: propertyName.toSnakeCase().toUpperCase())
}
