# SnakeYAML parser for Intellij IDEA platform IDEs

**YAML parser/highlighter**

Includes complete YAML loading pipeline under the hood. Capable of validating YAML on all levels.

Meant to serve a base dependency for all YAML-based languages - Ansible, Salt, Cloudify.

## Note for Professional/Ultimate edition users ##
Those editions already include basic YAML support. In case you want to enable this plugin, you'll need to reassign file type mappings:

    Go to `Preferences > Editor > File Types` and move `YAML` registered patterns to `SnakeYAML`.


## Original SnakeYAML features ##

* a **complete** [YAML 1.1 parser](http://yaml.org/spec/1.1/current.html). In particular, SnakeYAML can parse all examples from the specification.
* Unicode support including UTF-8/UTF-16 input/output.
* support for all types from the [YAML types repository](http://yaml.org/type/index.html).
* relatively sensible error messages.
