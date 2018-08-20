new GroovyShell().parse(new File('/home/thomas/Projects/bot/loading/ToLoad.groovy')).with {
    def (res, ctx) = answer('Thomas');
    println res
    println ctx
}