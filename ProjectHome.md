**Plain Java Rules aka PJR** is plain , simple rules engine (Non-JSR 94) built on MVEL Expression language.
Its easy to integrate and blazing fast.
For more details visit [WhereDoesItFitIn](WhereDoesItFitIn.md).
> PlainJavRules aka PJR is a logical processing engine built on MVEL scripts.
> At a granular level, the logical conditions are stored as MVEL scripts which are formed using the Data Model Object Attributes (i.e the object model passed to MVEL engine) and MVEL operators.

Expressions are defined using PJR ui , a webapp to define & manage Rules .
The rules are compiled and run on the fly on MVEL engine.

# Architecture #
![https://plainjavarules.googlecode.com/svn/wiki/rulesengine.png](https://plainjavarules.googlecode.com/svn/wiki/rulesengine.png)

**MVEL** powerful scripting language gives you the flexibility to define complex of scenarios/conditions.
Check out the MVEL language guide for details
http://mvel.codehaus.org/Language+Guide+for+2.0


Its speed in processing is lend by the powerful MVEL processing engine.
Check out the performance stats for MVEL in comparison with other expression languages
http://mvel.codehaus.org/Performance+of+MVEL+2.0







