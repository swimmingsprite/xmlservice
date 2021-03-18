(:<ul>{
for $x in doc("C:/Users/fchom/Desktop/xml/test/XQUERY/Books.xml")/books/book
where $x/price<40
order by $x/price
return <li>{data($x/title)}</li>
}</ul>:)

for $x in doc("C:/Users/fchom/Desktop/xml/test/XQUERY/Books.xml")/books/book
return if($x/@category="XML") then <XMLBOOK>{upper-case($x/title)}</XMLBOOK>
else <NONXML>{substring($x/title, 1, 3)}</NONXML>












