package polygon.commands;

/**
 * An entity which can assemble data to be sent to Discord.
 * @param <T> The type of the assembled data.
 */
interface DataAssembler<T> {
    /**
     * Assembles data to be sent to Discord.
     * @return The assembled data.
     */
    T assembleData();
}
