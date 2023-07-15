# Folder for Assignement 1

In	this	assignment,	students	will learn and	compare	data	representation	technologies
and	compare	different	options	concerning	size	and	encoding	speed.	As	a	result,	they
should	produce	a	pdf	report	with	an	evaluation	of	 three	different	options:	XML	vs.
XML	compressed	with	Gzip vs.	Google	Protocol	Buffers.	Students	may	opt	 for	 JSON
instead	 of	 XML. Other	 options	 are	 possible	 and	 welcome	 under	 the	 professor’s
guidance.


Students	 should	 perform	 their	 comparison	 taking	 into	 consideration	 multiple
parameters,	including programming	complexity,	serialization	size,	and	serialization
and	 deserialization	 speed. Size	 is	 crucial	 because	 it	 affects	 network	 transmission
speed	but	so	is	serialization	and	deserialization.	All	these	have	a	compounding	effect
when	requests	go	through	a	long	sequence	of	services. Students	should	try	to	be	as
objective	as	possible.	The	report	should	also	include	a	comparison	of	the	use	cases	for
each	technology.


For	fairness,	students	should define	a	common	data structure	that	they	will	use for
the	comparisons: a	many-to-one	relationship between	students	and	professors.	Each
student has	the	following	information:	a unique	identifier;	name; telephone;	gender;
birth	 date;	 registration	 date;	 and	 address.	 Each	 professor has	 a	 unique	 identifier;
name;	birth	date;	telephone;	and	address.	Each	professor	may	have	multiple	students,
but	students	may	have	at	most	one	professor.	Students	are	free	to	use	additional data
structures,	if	they	wish	to	exercise	specific	details	of	the	technologies,	but	they	should
not	simplify this	base	one.


Students	are	expected	to	code in	Java.	They	may	wish	to	try	very	large	sets	of	students
and	professors,	or	many	repetitions	of	the	operations, to	reduce	the	impact	of	random
components	in	performance times.	Students should	also	do	a	reasonable	number	of
experiments,	 to	 consider	 significant	 averages	 and	 standard	 deviations. Students
should	be	careful	about	the	places	in	the	code	where	they	register	time.	They	should
also	try	to	use	similar	code	as	much	as	possible	 for	the	text	and	binary	 formats,	to
improve	fairness. For	the	benefit	of	the	report,	students	might	want	to	measure	the
sizes	of	the	data	structures	in	memory if	they	manage to	do	that.
In	the	report,	students	should	properly	describe	the	conditions	of	the	experiments,
e.g.,	computer	characteristics,	technologies	and	libraries used,	their	versions,	and	so
on.	Students	should	take	notice	of	the	time	it	takes	to	initialize	data	structures	in	the
Protocol	 Buffers	 and	 separate	 this	 time	 from	 serialization/deserialization.	 The
experiment	should	be	repeatable	from	the	report. For	example, students	should	add
data	structures	and	the	points	of	code	where	they	measure	times. The	report	should
include	a	short	description	of	the	data	representation	formats	that	students	should
use	to	support a	brief	critical	discussion	of	results,	e.g.,	why	are	Protocol	Buffers faster
than	XML.







Final Delivery
• Students	 should	 deliver	 their	 pdf report	 at	 Inforestudante. Students	 should	 be
careful	about	the	size	they pick	for	the	report.	Useless	verbosity	is	not	rewarded.
• The	 report should	 be	 written	 by groups	 of	 2	 students.	 We	 do	 expect	 all	 the
members	of	 the	group	 to	be	 fully	aware	of	all	 the	parts	of	 the	code	used.	Work
together!
• Grades will	be	based	on	the	quality	of	the	report:	how	do	students	describe	the
data	 representation	 formats,	 the experiment, presentation, and	 discussion	 of
results;	 how	 careful	 were	 they	 while	 doing the	 experiments;	 and	 how	 many
experiments did	they	run,	to	cover	the	different	behaviors	of	the	technologies.	Be
careful	about	the	way	the	report	looks.
