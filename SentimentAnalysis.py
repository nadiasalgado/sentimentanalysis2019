from textblob import TextBlob 


str1 = 'i love Wits'
str2 = 'i hate Tuks'
str3 = 'i really like food at Wits'
str4 = 'i really do not like food at Tuks'

strs = []
strs.append(str1)
strs.append(str2)
strs.append(str3)
strs.append(str4)
    
wits = ['Wits'] #you can add another parameter here for wits such as 'protests' and it will find both wits and protests
Wits = [s for s in strs if any(xs in s for xs in wits)]

tuks = ['Tuks']
Tuks = [s for s in strs if any(xs in s for xs in tuks)]

def analysis(str):
    analysis = TextBlob(str)
    
    if analysis.sentiment.polarity > 0:
        return 'positive'
    if analysis.sentiment.polarity == 0:
        return 'neutral'
    else:
        return 'negative'
    
for s in Tuks:
    a = analysis(s)
    print (a)
    
for b in Wits:
    n = analysis(b)
    print (n)