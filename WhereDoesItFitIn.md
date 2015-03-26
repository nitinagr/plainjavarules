## Where does plainjavarules fit in ? ##

  * Simple UI to create/manage rules
  * Easy to customizable , as entire implementation is in Java
  * Add new functionalities/override existing functionality
  * No commercial obligation
  * No DSL language used
  * Ability to administer and define custom DSL
  * Simple to configure and use ,hence can be handed over to business people to manage.


I wanted a simple rules engine which gives me all the flexibility to adapt , yet not complex  and UI to manage  , eventually hand it over to business to manage simple rules and not come back to developers for every small change in business logic , i couldnt find one , hence i created one.


Here is a list of rules engines evaluated before creating the **plainjavarules**

## Drools ##
Drools is from Jboss.

**Pros:**
  * Supports most features.
  * It is pretty Stable.
  * Decent forum support.

**Cons:**
  * Heavy weight
  * Adverse Performance impact for high volume
  * Subjective expertise needed in setting up this system, moreover its complicated & steep learning curve for Developers
  * only the runtime environment is provided by Drools, tools and repository are supported only by Drools Guvnor
> > Effectively we need to deploy two systems for providing use and manage rules.
  * Doesn’t support strip down version GUI to only edit the drool rules on-the-fly ,need to install Guvnor (is the web application and repository to govern Drools and jBPM assets.)to manage rules
  * Only paid support

**Reference:**
http://onjava.com/pub/a/onjava/2005/08/24/drools.html?page=4
https://wiki.kuali.org/display/KULRICE/Kuali+Rice+-+Business+Rule+Management+System+%28BRMS%29+Evaluation#KualiRice-BusinessRuleManagementSystem%28BRMS%29Evaluation-DisadvantagesofDrools


## Ibm Jrules ##
Same as Jboss , supports most features and its one of the flagship product from IBM

**Pros:**
  * Please refer the exhaustive presentation on the pros http://publib.boulder.ibm.com/infocenter/ieduasst/v1r1m0/topic/com.ibm.iea.wcs/wcs/7.0.0.2/Integration/ILOGJrulesIntegrationOverview/player.html

**Cons:**
  * Licensed Software and expensive
  * Expertise needed in setup and integration



## Jrule Engine ##
.
**Pros:**
  * xml based
  * Simple and easy to learn
  * Suitable for developers as the logic is similar to writing a Java Program.(http://jruleengine.sourceforge.net/xmlcontent.html)

**Cons**
  * No GUI support
  * Not suitable for business users

**Reference:**
http://jruleengine.sourceforge.net/



## OpenL Tablets ##

**Pros:**
  * Rules are created in Excel
  * GUI support for business
  * Load the rule changes on the fly
  * LGPL license
  * Powerfull feature: Ability to  import Java Classes to the excel sheet and use the variables directly in the Tablets
  * Our Java Business Object Model as basis for the OpenL business Vocabulary

**Cons:**
  * Not much user base
  * Lacks of documentation on many features
  * Not all logic could be expressed as decision tables

**Reference:**
http://openl-tablets.sourceforge.net