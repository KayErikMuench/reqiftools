reqiftools
==========

A tool collection for handling the Requirements Interchange Format

Motivation or "why these tools?"
--------------------------------

Working in the requirements business isn't that easy.
Not only the engineering part but also the management calls for a
lot of tasks to do and to think about.

I don't know the origin of this advice, but for me it has always
been a leading light at the horizon:

If you can, automate!

Fortunately for managing requirements there is a standard
for exchanging them, developed under the Object Management Group,
the [Requirements Interchange Format](http://www.omg.org/spec/ReqIF/) ReqIF.

A good article (http://re-magazine.ireb.org/issues/2014-3-gaining-height/open-up/)
about the history and current state, written by Michael Jastram, can be found in the 
[Requirements Engineering Magazine](http://re-magazine.ireb.org/).

Not only there is a standard, there are some tools too.
One I would recommend is the [Eclipse Requirements Modeling Framework](https://www.eclipse.org/rmf/) RMF.

Due to its Eclipse-nature the main focus, I think, lies in building Eclipse based
tools, which is a well-founded goal, but not enough automation for me.
Fortunately again, the RMF not only includes an IDE for working with
ReqIF formatted files, called ProR, but also a number of plugins usable as
packages outside of Eclipse. They build the basis of this work.

The reqiftools shall wrap up the RMF packages to simplify creating and
parsing ReqIF formatted files, used by perhaps a maven based toolchain
or by yet another requirements tool.

ReqIF-Compiler
--------------

The ReqIF-Compiler simplifies the creation of a specification by shifting 
levels up and down in a hierarchy, marked up with headlines, and supplying requirements
by adding specification objects at the current level.

Requirements have only a few attributes:
* an ID,
* an XHTML-based description,
* an optional date,
* a status marker
* a priority marker an
* a risk marker.

ReqIF-Parser
------------

The ReqIF-Parser reads files in ReqIF-format and returns an RMF ReqIF object.


