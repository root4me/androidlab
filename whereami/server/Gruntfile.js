module.exports = function(grunt) {

  // Time how long tasks take.
  require('time-grunt')(grunt);

  // Load grunt tasks automatically
  require('load-grunt-tasks')(grunt);

  // Configurations
  var config = {
    app: 'app',
    dist: 'dist',
    srcjs: ['javascripts/*.*', '../bower_components/jquery/dist/jquery.js',
    '../bower_components/foundation-sites/dist/foundation.js'
  ],
  srcscss: ['scss/*.*'],
  srccss: ['stylesheets/*.*', '../bower_components/foundation-sites/dist/foundation.css'],
  srcimg: ['images/*.*'],
};

// Define the configuration for all the tasks
grunt.initConfig({
  // Project settings
  config: config,
  pkg: grunt.file.readJSON('package.json'),

  clean: {
    dist: {
      files: [{
        dot: true,
        src: ['.tmp', '<%= config.dist %>/*', '!<%= config.dist %>/.git*']
      }]
    }
  },

  processhtml: {
    dist: {
      files: [{
        expand: true,
        cwd: 'views',
        src: ['**/*.handlebars'],
        dest: '<%= config.dist %>/views',
        ext: '.handlebars'
      }],
    },
  },

  copy: {
    distimg: {
      files: [{
        expand: true,
        cwd: 'public',
        src: '<%= config.srcimg %>',
        dest: '<%= config.dist %>/public',
      }]
    },

    appFiles: {
      files: [{
        expand: true,
        src: ['app.js', 'config.js' , 'bower.json' , 'package.json', 'data/**/*','bin/**/*'],
        dest: '<%= config.dist %>',
      }]
    },

  },

  uglify: {
    options: {
      //                banner: '/*Packaged on : <%=grunt.template.today("yyyy-mm-dd")%>*/\n'
    },
    dist: {
      files: [{
        expand: true,
        flatten: true,
        extDot: 'last',
        /* Without this jquery.xyz.js and jquery.js at source will only produce one jquery.min.js at the destination folder
        Drove me nuts wondering why uglify seem to miss files. Turns out, i didnt read the doc close enough.
        But, why some one thought that setting extDot default to 'first' is the best thing to do is still a mystery. Extensions usually start after the last dot !  */
        cwd: 'public',
        src: '<%= config.srcjs %>',
        dest: '<%= config.dist %>/public/javascripts',
        ext: '.min.js'
      }]
    },
  },
  cssmin: {
    options: {
      processImport: false,
    },
    dist: {
      files: [{
        expand: true,
        flatten: true,
        cwd: 'public',
        src: '<%= config.srccss %>',
        dest: '<%= config.dist %>/public/stylesheets',
        ext: '.min.css'
      }]
    }
  },

  sass: {
    options: {
      sourceMap: true,
      style: 'expanded'
    },
    dist: {
      files: [{
        expand: true,
        cwd: 'scss',
        src: ['*.scss'],
        dest: 'public/stylesheets',
        ext: '.css'
      },
    ]
    }
  },
  htmlmin: {
    dist: {
      options: {
        removeComments: true,
        collapseWhitespace: true
      },
      files: [{
        expand: true,
        cwd: '<%= config.dist %>',
        src: '**/*.html',
        dest: '<%= config.dist %>',
      }]
    },

  },
  watch: {
    options: {
      livereload: true
    },
    scss: {
      files: 'scss/*',
      tasks: ['sass']
    },
    express: {
      files: ['**/*.js', '**/*.handlebars'],
      tasks: ['express:dev'],
      options: {
        spawn: false
      }
    }
  },

  express: {
    options: {

    },
    dev: {
      options: {
        script: 'bin/www',
      }
    }
  },
});

//grunt.registerTask('default', ['clean:dist', 'copy:srcimg', 'copy:distimg', 'processhtml', 'cssmin', 'uglify', 'htmlmin']);

grunt.loadNpmTasks('grunt-contrib-watch');
grunt.loadNpmTasks('grunt-express-server');

grunt.registerTask('default', ['usage']);
grunt.registerTask('dev', ['sass', 'express', 'watch']);
grunt.registerTask('build', ['clean:dist','sass', 'copy', 'processhtml', 'cssmin', 'uglify', 'htmlmin']);
grunt.registerTask('build-nomin', ['clean:dist','sass', 'copy:distimg', 'processhtml']);

grunt.registerTask('usage', 'display usage parameters', function() {
  console.log("usage :");
  console.log("\t grunt clean:dist - cleans /dist folder");
  console.log("\t grunt dev - development mode");
  console.log("\t grunt build - build and update minimized version to /dist folder");
  console.log("\t grunt build-nomin  - build and update non minified version to /dist folder");
});

// add sass to the grunt file

};
