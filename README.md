The program is created to search similar links in similar html pages.

Input parameters:
	Filename of source file
	Filename of destination file
	Id of link to search in source file (optional)

Main idea: Find an elem in source file by id and get it's attributes
Go through links and dest file and find the one with most matching params

Output for sample pages:
1: 
elem with max matches: <a class="btn btn-success" href="#check-and-ok" title="Make-Button" rel="done" onclick="javascript:window.okDone(); return false;"> Make everything OK </a>
path html > body > div > div > div > div > div > div

2: 
elem with max matches: <a class="btn test-link-ok" href="#ok" title="Make-Button" rel="next" onclick="javascript:window.okComplete(); return false;"> Make everything OK </a>
path html > body > div > div > div > div > div > div > div

