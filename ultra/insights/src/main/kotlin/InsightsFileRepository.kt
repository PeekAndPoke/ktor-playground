package de.peekandpoke.ultra.insights

import de.peekandpoke.ultra.depot.FileSystemRepository

class InsightsFileRepository : FileSystemRepository("insights", "./tmp/insights"), InsightsRepository
