slidenumbers: true

## Building Beautiful APIs
###### (you've got red on you)
### µCon '14 

Brendan McAdams ( @rit )
bmcadams@netflix.com

![original](images/SHAUN_AND_ED.png)

--- 
## [fit] Beauty Is In The Eye Of The Beholder

![right](images/piggy-kermit.jpg)

---


## [fit] ... But you can't always put lipstick on a pig
### [fit] and get good results.

![right original](images/lip-stick-on-a-pig.jpg)


---
### Beautiful APIs Should 
### Speak For Themselves

![left ](images/BrilliantPony.png)

- Some Questions
    1. If your code & API are hard to understand internally it's likely the same externally
    1. Is it monolithic and slow moving, or small and nimble?
    1. Is it well documented?
    1. Do you return well articulated, meaningful errors?

---


![left](images/alice-drink-me.jpg)

#[fit] Smaller is Better

* Build lots of small services

* Move quickly, move independently

* APIs are powerful in this model

* API can feed UI, Data Services, etc without a monolithic dev cycle

^ They can form something larger in aggregate

^ We build backend, UI team works in Angular, other teams pull data...

^ Think about billing service vs listings service vs accounts service vs etc


---
![right](images/cookie-monster-key.jpg)

## Think Hard About 
# Authentication

* Central authentication with shareable tokens is important 

* Make sure you can restrict who has access to what resources (authorization / authz)

* ~~This should strike through~~

* Super<sup>script</sup>

* Sub<sub>script</sub>

^ Yes, Virginia, there *is* an __important__ difference between authentication (auth) and authorization (authz)

---
#[fit] Choose a good service model

![left fit autoplay](images/majorKong.mov)

* Choose a service model that's easy to interact with (and develop)
* The same goes for your data layer, and development framework
* Your Mileage May Vary, but I happen to think quite fondly of JSON & REST

^ i.e. how will people communicate with your API?

--- 

#[fit] Code Is Communication

![left ](images/buck-phone.jpg)

* Good Code should be Self Documenting
* But there are also tools that can help
  - Apiary
  - Swagger

---
### [fit] A Little Swagger In Your Step

* Swagger can make API Development a **lot** easier
  - If another group of developers are interacting with your API
  - They will have interactive, detailed documentation at their fingertips

![right](images/Breaking-Bad-Heisenberg.jpg)

---
### [fit] A Little Swagger In Your Step

* Swagger is built around **self documenting code** 
  - Documentation is always up to date.
* Swagger itself is mostly a JSON based "Specification"
  - Scalatra module for describing API, generates JSON
  - `swagger-ui` JSON Frontend explores API

![right](images/Breaking-Bad-Heisenberg.jpg)


---
# ERRORS

![right fit](images/developers-developers-developers.jpg)

* Errors!
* Errors!
* Errors!

---

# ERRORS

![left fit](images/muppetspairprogramming.jpg)

* Meaningless error messages lead to long days of slaving over error logs
* Nothing sucks more than spending an hour digging through logs...
* ... only to find it was something you could have communicated with an error message


^ The net effect of bad error messaging can be deleterious & exhausting

^ "Oh, the User ID you specified was *technically* valid but had a malformed record"

---
## Good Practices for 
# Errors

* If you can validate an entire request object, do so
  - Return every error you know about immediately, and clearly
* If you can pass multiple errors up your stack and return them, do so... 
  - JSON Supports Arrays, after all

![left ](images/train-crash.jpg)

---
## Good Practices for 
# Errors

* Provide **useful data**, not a generic `500 Internal Server Error`
  - If you have an exception, extract a message from it
  - **However**: Exceptions are not flow control

![left](images/mushroom-cloud.jpg)


--- 
## Provide Errors That 
# *Make Sense*

* PHP Provides this useful error message:
  - `Parse error: syntax error, unexpected T_PAAMAYIM_NEKUDOTAYIM`
* W. T. F. ?

^ Hebrew for "Double Colon". 

![right](images/jackie-chan-wtf.png)

---
## Provide Errors That 
# *Make Sense*

* 'Paamayim Nekudotayim' (פעמיים נקודתיים) is apparently Hebrew for "Double Colon"
* The main authors of PHP 4+ are Israeli, and probably thought this was funny.
* Imagine how many hapless devs have misplaced '::' and spent hours scratching their heads...

![right](images/jackie-chan-wtf.png)

---
##[fit] Sensible Errors

* Most API platforms have some sort of Status Code mechanism
* For REST, Pick & Document HTTP Codes 
* BE CONSISTENT

![left](images/youngfrankenstein-igor-brain.jpg)

---
##[fit] Sensible Errors

* Even if you make up your own silly HTTP codes, make sure you document them so people know what they mean.
* There's a proposed HTTP Error Code "`759 - Unexpected T_PAAMAYIM_NEKUDOTAYIM`"
* I also like "`763 - Under-Caffeinated`"

![left](images/youngfrankenstein-igor-brain.jpg)

---
####[fit] Testing Your API

![right](images/bladerunner-V-K-cropped.png)

* Your API Framework of choice should provide good tools for testing it
* If it doesn't, consider finding a better framework
* Scalatra (the framework I'm using) provides great bindings to Scalatest
* This contributes greatly to feeling comfortable that what you've built works as documented

^ I Have Some thoughts on what kinds of tests you should write...


---
###[fit] Write Lots of Tests

* Positive Tests
* Negative Tests
* Service Level Tests (HTTP Calls)
* Helper Level Tests (backend objects sans HTTP)
* Parser Tests
* Regression Tests 
* Voight-Kampff Tests

![right](images/bladerunner-V-K-cropped.png)

---

#[fit] One Test, Two Test, 
#[fit] Red Test, Blue Test...

![left](images/redfishbluefish.jpg)

---

> "Never Trust A Test You Haven't Seen Fail."
-- Rob Fletcher ( @rfletcherEW )
####[fit] ... who says he nicked it from @tddmonkey (Colin Vipurs)

![left](images/fletcher.jpeg)

---

### [fit] Questions?

![original](images/Dr-Strangelove-George-C.Scott_.jpg)

---

```scala
package net.evilmonkeylabs.yape

import org.pegdown.PegDownProcessor

object MarkdownParser extends App {
  val parser = new PegDownProcessor
  val file = try {

    args(0)
  } catch {
    case t: Throwable =>
      throw new Exception("You must specify a file to parse", t)
  }
  val source = scala.io.Source.fromFile(file)
  val lines = source.mkString
  source.close()
  val slides = lines.split("\n---")
  println(s"# of slides: ${slides.length}")

  for (slide <- slides) {
    println(parser.markdownToHtml(slide))
    println("***********************************")
  }
}
```

---

Block Formulas are parseable, and will eventually do pretty rendering via MathJax

$$
\left( \sum_{k=1}^n a_k b_k \right)^2 \leq \left( \sum_{k=1}^n a_k^2 \right) \left( \sum_{k=1}^n b_k^2 \right)
$$

I hope to hell that I get them working...

---

Also supportable is inline formulas...

The slope $$a$$ of the line defined by the function $$f(x) = 2x$$ is $$a = 2$$.





