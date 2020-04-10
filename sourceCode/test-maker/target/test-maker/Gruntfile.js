/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao []
 * Date: 2015/1/22
 * Time: 18:29
 */
module.exports = function (grunt) {
    'use strict';
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        less: {
            development: {
                options: {
                    strictMath: true,
//                    sourceMap: true,
                    paths: ["assets/css"],
                    dumpLineNumbers: true
                },
                files: {"assets/css/app.css": "assets/css/app.less"}
            },
            production: {
                options: {
                    strictMath: true,
                    paths: ["assets/css"],
                    plugins: [
                        new (require('less-plugin-autoprefix'))({browsers: ["last 2 versions"]}),
                        new (require('less-plugin-clean-css'))()
                    ]
                },
                files: {"assets/css/app.min.css": "assets/css/app.less"}
            }
        },

        bowercopy: {
            options: {
                srcPrefix: 'bower_components'
            },
            scripts: {
                options: {
                    destPrefix: 'lib'
                },
                files: {
                    'bootstrap-datepicker/js/bootstrap-datepicker.js': 'bootstrap-datepicker/js/bootstrap-datepicker.js',
                    'bootstrap-datepicker/css/datepicker3.css': 'bootstrap-datepicker/css/datepicker3.css',
                    'bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js': 'bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js',
                }
            }
        },

        jshint: {
            options: {
                jshintrc: '.jshintrc'
            },
            files: {
                src: ['Gruntfile.js','assets/js/**/*.js']
            }
        },
        uglify:{
            options: {
                compress: {
                    drop_console: true
                }
            },
            dist: {
                files: [{
                    expand: true,
                    cwd: '../../../target/<%= pkg.name %>/assets/js',
                    src: '**/*.js',
                    dest: '../../../target/<%= pkg.name %>/assets/js'
                }]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-bowercopy');


    grunt.registerTask('css', ['less']);
    grunt.registerTask('default', ['css']);
    grunt.registerTask('dist', ['less:production','uglify:dist']);
};
