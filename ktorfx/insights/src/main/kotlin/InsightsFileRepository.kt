package de.peekandpoke.ktorfx.insights

import de.peekandpoke.ultra.depot.FileSystemRepository

class InsightsFileRepository : FileSystemRepository("insights", "./tmp/insights"), InsightsRepository
