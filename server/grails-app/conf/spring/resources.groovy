import demo.CorsFilter
import demo.CustomAppRestAuthTokenJsonRenderer
import demo.UserPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    corsFilterTest(CorsFilter)
    accessTokenJsonRenderer(CustomAppRestAuthTokenJsonRenderer)
}
