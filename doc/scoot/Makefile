language_subset.pdf: *.tex refs.bib
	pdflatex language_subset
	bibtex language_subset
	pdflatex language_subset
	pdflatex language_subset

clean:
	rm -f *.aux *.bbl *.blg *.log *.toc *.dvi *.out *.tdo *.gx.table *.gx.gnuplot language_subset.ps language_subset.pdf *~
