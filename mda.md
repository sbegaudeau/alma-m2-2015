# Presentations

- [Xtext and Sirius](https://github.com/sbegaudeau/alma-m2-2015/blob/gh-pages/Xtext_Sirius.pdf)
- [Sirius - Stronger, Faster, Smarter Diagram editors](http://mporhel.github.io/slides/2015_EclipseConEU_Sirius31_StrongerFasterSmarter/#/)
- [Because you can't fix what you don't know is broken](https://github.com/sbegaudeau/alma-m2-2015/blob/gh-pages/fix.pdf)


# Instructions

During this project, you will have to create a Sirius-based designer including multiple viewpoints and representations. You will have to use the metamodel, already defined, in the project fr.univnantes.alma.angular that you will be able to find in this repository.

## Getting started

Create a new Viewpoint Specification Project named fr.univnantes.alma.angular.design. This project will contain the configuration of your designer. Create another project, a Modeling Project this time, named fr.univnantes.alma.angular.modeling. In this project, you will create a model, using the Angular metamodel and you will manipulate it using the designer that you will create.

## Creating a model

Open the file angular.ecore located in the project fr.univnantes.alma.angular and right click on the root concept named Project. Select the action "Create a dynamic instance" and save the model in a file named "angular.xmi" at the root of the modeling project (fr.univnantes.alma.angular.modeling). Now that you have created an empty model from an ecore metamodel, you can open it using "Open With" with the "Sample Reflective Ecore Model Editor" to edit it using a tree-based approach.

In this model, add some Folders under the project with some Modules and ESClasses inside. To start creating those elements, right click in the model on the root concept Project and select "New Child" to start creating a Folder (repeat on the folder to create the modules, etc). Give each of them a meaningful name. For example, your project could be named "TestProject" with a folder named "app" inside and two modules with the path "app/users.js" and "app/common.js". In the module "app/users.js", you could have several classes named "NewUser", "Users", "User" and "UpdateUser". Those classes could be used as DTO for the modification of the users of the application.

## Architecture viewpoint

In this project, you will create at least two viewpoints, the first one will be dedicated to the technical information of your project. Create in the file with the extension '.odesign' in the Viewpoint Specification Project that you have created (fr.univnantes.alma.angular.design) a new viewpoint named "Architecture". This viewpoint should support files with the extension "xmi". Beneath this viewpoint, create a diagram named Class Diagram.

### Class Diagram

Your Class Diagram should reference the metamodel angular.ecore from the workspace in order to tell Eclipse Sirius not to care about other potential metamodels (add the project of the metamodel in the MANIFEST.MF file and add a reference to your ecore file in the workspace from the Diagram). Your Class Diagram should use the domain class angular.Module since it represents an ES6 module which can contain various pieces of code (including Classes). Then you will create inside the default layer of your Class Diagram named "Class Diagram", a list Container for the concept angular.ESClass named "CD_Class".

#### Style

In order to create a proper class diagram, use a gradient as the style. Make sure that your gradient is a gradient from white to light blue. Make sure that your gradient style uses rounded corner with an arc of 5px for the height and the width of the corners. Finally, use "12" as the height computation expression and "15" as the width computation expression. In order to have a nice diagram, you need to make sure that most of the elements with a similar role have the same style and size.

In your designer, create a Palette named "Default Colors" and inside create an user defined color named "Default Class". Use the following RGB values for the color (210, 228, 252). Now modify your gradient to use this color instead of the light blue.

Now right click on your modeling project and select the Architecture viewpoint. Double click on your angular.xmi file and use the tree-based editor from EMF to create inside of the project various modules and classes. Once you have created a meaningful example, right click on a module to create an instance of your Class Diagram.

The Class Diagram should display the classes in your module.

#### Container Creation Tool

In your layer, create a new section named "Elements" with a container creation tool name "New ESClass" inside. This tool should let you create the mapping previously defined. In the begin step of the operation change the context of the operation using the expression "var:container". Your tool should then create a new instance under the change context. The creation of an ESClass can only be realized in a module, as such it can only be created in the feature moduleElements of a module. Make sure that your tool create the new instance of "angular.ESClass" in the reference "moduleElements". Then add an operation to set the name of the newly created object to "NewClass". Do not forget to specify the kind of mapping that should be created by the tool.

Test your tool in the diagram to ensure that it works.

#### Direct Edit

Create a section in your Class Diagram named Behavior Tools. Inside, create a direct edit tool named "Classifier Direct Edit" which will be used to edit the name of the mapping "CD_Class". In the begin operation of this direct edit tool, you will only use a "Set" operation to modify the value of the feature "name" with the content of the mask variable with the value expression "var:arg0".

You can now modify the name of a class in your diagram just by typing a new name once the class has been selected.

#### Java Service

Open the file MANIFEST.MF and go to the dependencies tab to add a dependency to the OSGi bundle "com.google.guava". Then in the source folder "src" of your project, create a Java class named NamingServices in the package fr.univnantes.alma.angular.internal.services. In this Java class, create a method named "toCamelCase" using as parameters, first an EObject (from org.eclipse.emf.ecore) and a String (from java.lang) with the following code.

```
public String toCamelCase(EObject eObject, String word) {
	if (word != null) {
		StringBuffer buffer = new StringBuffer(word.length());
		for (String part : Splitter.on(CharMatcher.WHITESPACE).trimResults().split(word)) {
			buffer.append(toU1Case(part));
		}
		return buffer.toString();
	}
	return word;
}

private String toU1Case(String word) {
	if (word != null && word.length() > 0) {
		return new StringBuilder(word.length()).append(Ascii.toUpperCase(word.charAt(0))).append(
				word.substring(1)).toString();
	}
	return word;
}
```

This code will use Google Guava in order to convert the word in camel case. You can now modify your direct edit tool to replace the value expression "var:arg0" with the expression "aql:self.toCamelCase(arg0)". Add a Java extension to your viewpoint using the qualified name of your Java class (fr.univnantes.alma.angular.internal.services.NamingServices). You should now be able to type "Great abstract class" as the name of a Class using the direct edit and see it transformed to "GreatAbstractClass".

#### Conditional style

Now create a conditional style under your class mapping. You will use the predicate expression "feature:abstract", used to indicate that the conditional style should be used if the property "abstract" of the domain class of your mapping (in our case angular.ESClass) has the value "true". Create another color named "Abstract Class" in your Palette with the following values (240, 216, 216). Under this conditional style, copy the style that you have created before but this time change the color to have a gradient from white to the color "Abstract Class"

#### Submappings

Now you will create child nodes for the Class. For that create two child nodes mapping using the domain class angular.Field for the first one and angular.Function for the second one. Add a square style to those nodes. Create in the "Elements" section two new node creation tools to create a field or a function in a class.

#### Label

Edit the label of the Field and the Function mapping to display the name and the type of the field and the name, type and parameters of the function. Add two direct edit tools to edit the field and function.

#### Relation-based edges

Create a new relation-based in your layer named Extends to show that a class extends another class (use the target finder expression "feature:extends"). Add a new tool in the Elements section in order to make a class extend another one.

You now have a very basic Class diagram, we will start the creation of other diagram.

#### Deletion

Create the necessary delete tools to be able to delete the class, one of its field, one of its function or the extends relationship.

### Explorer Diagram

The explorer diagram will be used to view the structure of the project. This diagram will be created on a domain class "angular.Container" and it will display using node mappings the folders, the modules and the pages contained in one folder (use semantic candidates expression like "feature:pages", "feature:folders", "feature:").

Copy the icons available in the project fr.univnantes.alma.angular into the Viewpoint Specification Project and use them as the style of your folders, modules and pages. Make sure to use "15" as your size computation expression in order to set the size of your images.

Use a conditional style to use the empty folder icon if a folder does not have any pages or modules (use something like "aql:self.pages.size() + self.modules.size() = 0" as the predicate expression).

#### Representation Creation Tool

Create a representation creation tool in your explorer diagram in order to create a Class Diagram by right clicking on a Module mapping in your Explorer diagram.

#### Behavior tool

Create a double click tool for the mapping folder. In the begin part of this tool, add a navigation operation to navigate to an Explorer Diagram. Make sure that this diagram will be created if it does not exist.

You should now be able to easily create new folders, pages and modules in your diagram and navigate inside easily.

### Components Diagram

We will now create a brand new kind of Diagram, a component one. This diagram will be defined on a "angular.Project". In this diagram, you will have to display the various NgModules of your project using a free form container mapping. Inside, as child nodes, you will have to display the various services, controllers, and directives available. Add the necessary tools and mappings to create, display and delete those elements.

Display the relationships between the services, the controllers and the directives.

## Review viewpoint

Create a second viewpoint named "Review" that will be used to review some concepts of your models.

### Documentation table

Create a table used to display all the ESClass of your model with their properties.

## Not synchronized Diagram

Make your Class diagram not synchronized (have a look at [Dart Designer](https://github.com/dartdesigner/Dart-Designer/tree/master/bundles/org.obeonetwork.dsl.dart.design/description) for inspiration). For that, add new tools to add and remove existing elements of your model in your diagram and make sure that your node/container mapping of the Class Diagram are not synchronized.
