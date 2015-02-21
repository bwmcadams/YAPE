
package net.evilmonkeylabs.yape

import org.pegdown.plugins.ToHtmlSerializerPlugin
import org.pegdown.{DefaultVerbatimSerializer, VerbatimSerializer, LinkRenderer, ToHtmlSerializer}

import scala.collection.JavaConverters._
/**
 * A port of Pegdown's HTML Renderer focused on the necessary HTML rendering for
 * YAPE slides. Specifically, adds CSS Classes for certain types of data based on plugin feeds
 * and fixes a few other quirks such as stripping out Image ALT tags as contextual rather than mouseover data (
 * fit image, original size, autoplay, etc )
 */
class SlideHTMLRenderer(val linkRenderer: LinkRenderer,
                        val verbatimSerializers: Map[String, VerbatimSerializer] = Map(VerbatimSerializer.DEFAULT, DefaultVerbatimSerializer.INSTANCE),
                        val plugins: List[ToHtmlSerializerPlugin] = List.empty)
  extends ToHtmlSerializer(linkRenderer, verbatimSerializers.asJava, plugins.asJava) {

}
