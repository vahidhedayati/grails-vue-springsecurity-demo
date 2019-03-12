package demo

import grails.core.GrailsApplication
import grails.core.support.GrailsApplicationAware
import grails.gorm.transactions.Transactional

class UtilService implements GrailsApplicationAware {

    GrailsApplication grailsApplication
    
    boolean containsHanScript(String s) {
        for (int i = 0; i < s.length(); ) {
            int codepoint = s.codePointAt(i);
            i += Character.charCount(codepoint);
            if (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN) {
                return true;
            }
        }
        return false;
    }

    User getAdminUser() {
        return User.findByUsername('susan')
    }
    /**
     * This was a repeated function all over the place
     * Centralised since it is the same function for different domain classes
     *
     * @String className -- pass in the class simpleName  = becomes actual class
     * @int codeSize -- integer value of how long code should be
     * @String name -- name of code
     *
     * @returns code as per class requirement
     */
    @Transactional
    String mapCode(String className,int codeSize,String name) {
        def clazz = grailsApplication?.domainClasses?.find {it.clazz.simpleName == className }?.clazz
        String code = name?.trim()?.replaceAll("[^a-zA-Z0-9]+","")?.replaceAll("[^\\x00-\\x7F]", "")
        if (clazz) {
            if (code && (code.length() >= codeSize || containsHanScript(code))) {
                code = code.substring(0, codeSize)
                def c = clazz.findByCode(code)
                if (c) {
                    boolean found = false
                    while (found == false) {
                        code = org.apache.commons.lang.RandomStringUtils.random(codeSize, true, true)
                        if (code) {
                            c = clazz.findByCode(code)
                            if (!c) {
                                found = true
                            }
                        }
                    }
                }
            } else {
                if (code.length() <= codeSize) {
                    String code1=code
                    code = org.apache.commons.lang.RandomStringUtils.random(codeSize-code.length() , true, true)
                    code="${code1}${code}"
                } else {
                    code = org.apache.commons.lang.RandomStringUtils.random(code ? codeSize - code.length() : codeSize, true, true)
                }

                if (code) {
                    def c = clazz.findByCode(code)
                    if (c) {
                        boolean found = false
                        while (found == false) {
                            code = org.apache.commons.lang.RandomStringUtils.random(codeSize, true, true)
                            if (code) {
                                c = clazz.findByCode(code)
                                if (!c) {
                                    found = true
                                }
                            }
                        }
                    }
                }
            }
        } else {
            code = code.substring(0, codeSize)
        }
        return code.toUpperCase()
    }

}
