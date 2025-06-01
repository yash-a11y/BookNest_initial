package org.ycode.book_nest.book.data.dto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement

object BookWorkDtoSerializer: KSerializer<BookWorkDto> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        BookWorkDto::class.simpleName!!
    ) {
        element<String?>("description")
    }

    override fun deserialize(decoder: Decoder): BookWorkDto = decoder.decodeStructure(descriptor) {
        var description: String? = null

        try {
            while(true) {
                when(val index = decodeElementIndex(descriptor)) {
                    0 -> {
                        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                            "This decoder only works with JSON."
                        )
                        val element = jsonDecoder.decodeJsonElement()
                        description = try {
                            if(element is JsonObject) {
                                decoder.json.decodeFromJsonElement<DescriptionDto>(
                                    element = element,
                                    deserializer = DescriptionDto.serializer()
                                ).value
                            } else if(element is JsonPrimitive && element.isString) {
                                element.content
                            } else null
                        } catch (e: Exception) {
                            null
                        }
                    }
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index $index")
                }
            }
        } catch (e: Exception) {
            // Return a default BookWorkDto with null description if parsing fails
            return@decodeStructure BookWorkDto(null)
        }

        return@decodeStructure BookWorkDto(description)
    }

    override fun serialize(encoder: Encoder, value: BookWorkDto) = encoder.encodeStructure(
        descriptor
    ) {
        value.description?.let {
            encodeStringElement(descriptor, 0, it)
        }
    }
}