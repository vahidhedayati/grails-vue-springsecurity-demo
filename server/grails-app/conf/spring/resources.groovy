import demo.CorsFilter
import demo.UserPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    corsFilterTest(CorsFilter)
}
